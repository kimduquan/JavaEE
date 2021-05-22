import org.jppf.client.JPPFClient;
import org.jppf.client.JPPFJob;
import org.jppf.node.protocol.ScriptedTask;
import javax.script.*;
var client = new JPPFClient();
var job = new JPPFJob();
var bindings = new HashMap<String, Object>();
var jsTask = new ScriptedTask<String>("js", "'this is a javascript test';", "js", bindings);
job.add(jsTask);
var groovyTask = new ScriptedTask<String>("groovy", "'this is a groovy test';", "groovy", bindings);
job.add(groovyTask);
//-Dpython.import.site=false
var pythonTask = new ScriptedTask<String>("python", "'this is a python test'", "python", bindings);
job.add(pythonTask);
//var scalaTask = new ScriptedTask<String>("scala", "println(\"Hello, World!\")", "scala", bindings);
//job.add(scalaTask);
var manager = new ScriptEngineManager();
var res = client.submitJob(job);
System.out.println(res.get(0).getResult().toString());
System.out.println(res.get(1).getResult().toString());
System.out.println(res.get(2).getResult().toString());
//System.out.println(res.get(3).getThrowable().toString());
client.close();
/exit