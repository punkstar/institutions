package uk.ac.bath.cs.agents.asinst;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.iids.aos.service.AbstractDefaultService;

/**
 * 
 * @author Nick Jones <nj210@bath.ac.uk>
 *
 */

public class ClingoServiceImpl extends AbstractDefaultService implements ClingoService {
	protected String _instalLibDirectory = "/Users/nrj/Downloads/instal/instal/";
	protected String _clingo = "/Users/nrj/bin/clingo";
	
	private File _fileDirectory = new File("/Users/nrj/agentscape/asp");
	
	public ClingoServiceImpl() {
		super();
		this.__log("Service online");
		this._cleanDirectory();
	}
	
	public String[] solve(URL url, int answer_sets) {
		try {
			this.__log(String.format("Solve request received (URL at %s)", url.toString()));
			
			File asp_file = this._writeUrlToFile(url);
			
			return this._extractFluents(this._processFile(asp_file, answer_sets));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public String[] solve(String asp, int answer_sets) {
		try {
			this.__log("Solve request received (String)");
			
			File asp_file = this._writeStringToFile(asp);
			
			return this._extractFluents(this._processFile(asp_file, answer_sets));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public String[] solve(String asp) {
		return this.solve(asp, 1);
	}
	
	public String[] solve(URL url) {
		return this.solve(url, 1);
	}
	
	protected BufferedReader _processFile(File f, int answer_sets) throws IOException {
		String command = this._buildCommand(f, answer_sets);
		
		this.__log("Calling clingo: " + command);
		Process p = Runtime.getRuntime().exec(command);
		InputStream p_out = p.getInputStream();
		InputStream p_err = p.getErrorStream();
		
		BufferedReader p_out_reader = new BufferedReader(new InputStreamReader(p_out));
		
		return p_out_reader;
	}
	
	protected String[] _extractFluents(BufferedReader reader) throws IOException {
		ArrayList<String> fluents = new ArrayList<String>();
		
		Matcher m;
		Pattern p = Pattern.compile("holdsat\\((.*?)\\)\\s");
		
		String line;
		while((line = reader.readLine()) != null) {
			m = p.matcher(line);
			
			while(m.find()) {
				fluents.add(m.group());
			}
 		}
		
		return fluents.toArray(new String[] {});
	}
	
	protected File _writeStringToFile(String s) throws IOException {
		File f = this.__createTempFile();
		
		if (f.canWrite()) {
			BufferedWriter writer =  new BufferedWriter(new FileWriter(f));
			writer.write(s);
			writer.close();
		} else {
			throw new IOException(String.format("Can't write to file %s", f.getAbsoluteFile()));
		}
		
		return f;
	}
	
	protected File _writeUrlToFile(URL u) throws IOException {
		InputStream url_stream = u.openStream();
		BufferedReader url_reader = new BufferedReader(new InputStreamReader(url_stream));
		
		File f = this.__createTempFile();
		
		if (f.canWrite()) {
			BufferedWriter writer = new BufferedWriter(new FileWriter(f));
			
			String line;
			while ((line = url_reader.readLine()) != null) {
				writer.write(line);
			}
			
			writer.close();
			url_reader.close();
		}
		
		return f;
	}
	
	protected String _buildCommand(File filename, int steps) {
		return String.format(String.format("%s -n %d %s", this._clingo, steps, filename.getAbsoluteFile()));
	}
	
	protected void _cleanDirectory() {
		return;
	}
	
	private File __createTempFile() throws IOException {
		File f = File.createTempFile("clingo", null, this._fileDirectory);
		this.__log(String.format("Temp file created: %s", f.getAbsoluteFile().toString()));
		return f;
	}
	
    private void __log(String message) {
        Log.message(String.format("[clingo] %s", message));
    }
}