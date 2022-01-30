var dir = System.getProperty("dir");
var cmd = System.getProperty("cmd");
System.out.println(cmd);
var args = cmd.split(" ");
var ext = "";
if(System.getProperty("os.name").contains("Windows")) { ext = ".exe"; };
if(System.getProperty("os.name").contains("Linux")) { ext = ""; };
args[0] = args[0] + ext;
var builder = new ProcessBuilder(args);
builder.inheritIO();
if(dir != null) { builder.directory(Paths.get(dir).toFile()); }
var process = builder.start();
var wait = System.getProperty("wait");
if(wait != null) { process.waitFor(); }
/exit