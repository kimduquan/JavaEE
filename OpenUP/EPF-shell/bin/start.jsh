var args = System.getProperty("args").split(" ");
var ext = "";
var absPath = Paths.get(args[0]).toFile().getAbsolutePath();
if(System.getProperty("os.name").contains("Windows")) { ext = ".bat"; };
if(System.getProperty("os.name").contains("Linux")) { ext = ""; };
args[0] = absPath + ext;
System.out.println(args[0]);
var process = new ProcessBuilder(args);
process.start().waitFor();
/exit