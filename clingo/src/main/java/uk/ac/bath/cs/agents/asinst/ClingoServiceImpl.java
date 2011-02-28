package uk.ac.bath.cs.agents.asinst;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.iids.aos.service.AbstractDefaultService;

public class ClingoServiceImpl extends AbstractDefaultService implements ClingoService {
	protected String _clingo = "/Users/nrj/bin/clingo";
	
	private File _fileDirectory = new File("/Users/nrj/agentscape/asp");
	
	public ClingoServiceImpl() {
		super();
		this.__log("Service online");
		this._cleanDirectory();
	}
	
	public String[] solve(String asp, int answer_sets) {
		try {
			this.__log("Solve request received");
			
			File asp_file = this._writeStringToFile(asp);
			this.__log(String.format("Temp file created: %s", asp_file.getAbsoluteFile().toString()));
			
			String command = this._buildCommand(asp_file, answer_sets);
			
			this.__log("Calling clingo: " + command);
			Process p = Runtime.getRuntime().exec(command);
			InputStream p_out = p.getInputStream();
			InputStream p_err = p.getErrorStream();
			
			BufferedReader p_out_reader = new BufferedReader(new InputStreamReader(p_out));
			
			String line;
			
			while((line = p_out_reader.readLine()) != null) {
				this.__log(line);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public String[] solve(String asp) {
		return this.solve(asp, 1);
	}
	
	protected File _writeStringToFile(String s) throws IOException {
		File f = File.createTempFile("clingo", null, this._fileDirectory);
		
		if (f.canWrite()) {
			BufferedWriter writer =  new BufferedWriter(new FileWriter(f));
			writer.write(s);
			writer.close();
		} else {
			throw new IOException(String.format("Can't write to file %s", f.getAbsoluteFile()));
		}
		
		return f;
	}
	
	protected String _buildCommand(File filename, int steps) {
		return String.format(String.format("%s -n %d %s", this._clingo, steps, filename.getAbsoluteFile()));
	}
	
	protected void _cleanDirectory() {
		return;
	}
	
    private void __log(String message) {
        Log.message(String.format("[clingo] %s", message));
    }
}