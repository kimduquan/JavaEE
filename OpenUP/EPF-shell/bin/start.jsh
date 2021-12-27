System.out.println(System.getProperty("args"));
var args = System.getProperty("args").split(" ");
var ext = "";
var absPath = Paths.get(Paths.get(args[0]).toFile().getAbsolutePath());
var workDir = absPath.getParent();
if(System.getProperty("os.name").contains("Windows")) { ext = ".bat"; };
if(System.getProperty("os.name").contains("Linux")) { ext = ""; };
args[0] = absPath.toString() + ext;
//args[0] = absPath.getFileName().toString() + ext;
var builder = new ProcessBuilder(args);
builder.inheritIO();
//builder.directory(workDir.toFile());
var process = builder.start();
var async = System.getProperty("async");
if(async == null) { process.waitFor(); }
/exit