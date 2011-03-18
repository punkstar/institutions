package uk.ac.bath.cs.agents.asinst;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import org.iids.aos.service.AbstractDefaultService;

/**
 * 
 * An AgentScape service to provide access to Clingo, currently specialised to look for
 * holdsat(.*) results in the output.
 * 
 * @author Nick Jones <nj210@bath.ac.uk>
 *
 */

public class ClingoServiceImpl extends AbstractDefaultService implements ClingoService {
	protected String _instalLibDirectory = "/Users/nrj/Desktop/institutions/clingo/src/main/asp/";
	protected String _clingo = "/Users/nrj/bin/clingo";
	
	private File _fileDirectory = new File("/Users/nrj/agentscape/asp");
	
	public ClingoServiceImpl() {
		super();
		this.__log("Service online");
		this._cleanDirectory();
	}
	
	/**
	 * Entry method.  Solve the asp hosted at a URL.
	 */
	public ClingoResponse solve(URL url, int answer_sets) throws ClingoException {
		try {
			//this.__log(String.format("Solve request received (URL at %s)", url.toString()));
			
			File asp_file = this._writeUrlToFile(url);
			
			return this._buildResponse(this._processFile(asp_file, answer_sets));
		} catch (Exception e) {
			this.__log(String.format("Unable to solve: %s", e.getMessage()));
			throw new ClingoException(e.getMessage());
		}
	}
	
	/**
	 * Entry method.  Solve the asp held in the string.
	 * @throws Exception 
	 */
	public ClingoResponse solve(String asp, int answer_sets) throws ClingoException {
		try {
			//this.__log("Solve request received (String)");
			
			File asp_file = this._writeStringToFile(asp);
			
			return this._buildResponse(this._processFile(asp_file, answer_sets));
		} catch (Exception e) {
			this.__log(String.format("Unable to solve: %s", e.getMessage()));
			throw new ClingoException(e.getMessage());
		}
	}
	
	public ClingoResponse solve(String asp) throws ClingoException {
		return this.solve(asp, 1);
	}
	
	public ClingoResponse solve(URL url) throws ClingoException {
		return this.solve(url, 1);
	}
	
	/**
	 * Actually execute clingo
	 * 
	 * @param f
	 * @param answer_sets
	 * @return
	 * @throws IOException
	 * @throws InterruptedException 
	 */
	protected String _processFile(File f, int answer_sets) throws IOException, InterruptedException {
		String command = this._buildCommand(f, answer_sets);
		
		this.__log("Calling clingo: " + command);
		Process p = Runtime.getRuntime().exec(command);
		InputStream p_out = p.getInputStream();
		InputStream p_err = p.getErrorStream();
		
		BufferedReader p_out_reader = new BufferedReader(new InputStreamReader(p_out));
		
		StringBuilder builder = new StringBuilder();
		
		String line;
		while((line = p_out_reader.readLine()) != null) {
			builder.append(line);
 		}
		
		p_out_reader.close();
		p_out.close();
		p_err.close();
		
		return builder.toString();
	}
	
	/**
	 * Search the output for holdsat(.*) statements
	 * 
	 * @param reader
	 * @return
	 * @throws IOException
	 */
	protected ClingoResponse _buildResponse(String result) throws IOException {
		return ClingoResponse.build(result);
	}
	
	/**
	 * Write the contents of a string to a file
	 * 
	 * @param s
	 * @return
	 * @throws IOException
	 */
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
	
	/**
	 * Write the contents of the URL to a file
	 * 
	 * @param u
	 * @return
	 * @throws IOException
	 */
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
	
	/**
	 * Construct the command to execute clingo
	 * 
	 * @param filename
	 * @param steps
	 * @return
	 */
	protected String _buildCommand(File filename, int steps) {
		return String.format(
	        "%s -n %d %s %s %s",
	        this._clingo, steps,
	        this._instalLibDirectory + "alltrace.lp",
	        this._instalLibDirectory + "basic.lp",
	        filename.getAbsoluteFile()
	    );
	}
	
	/**
	 * Remove all of the old files from the output directory
	 * 
	 * @TODO Implement
	 */
	protected void _cleanDirectory() {
		return;
	}
	
	/**
	 * Create a uniquely named file
	 * 
	 * @return
	 * @throws IOException
	 */
	private File __createTempFile() throws IOException {
		File f = File.createTempFile("clingo", null, this._fileDirectory);
		//this.__log(String.format("Temp file created: %s", f.getAbsoluteFile().toString()));
		return f;
	}
	
    private void __log(String message) {
        Log.message(String.format("[clingo] %s", message));
    }
}