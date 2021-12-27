System.out.println(System.getProperty("args"));
var args = System.getProperty("args").split(" ");
var ext = "";
var workDir = Paths.get(args[0]).getParent();
var absPath = Paths.get(args[0]).toFile().getAbsolutePath();
if(System.getProperty("os.name").contains("Windows")) { ext = ".bat"; };
if(System.getProperty("os.name").contains("Linux")) { ext = ""; };
args[0] = absPath + ext;
var builder = new ProcessBuilder(args);
builder.inheritIO();
builder.directory(workDir.toFile());
var process = builder.start();
var async = System.getProperty("async");
if(async == null) { process.waitFor(); }
/exit