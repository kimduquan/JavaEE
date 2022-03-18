var dir = System.getProperty("dir", "");
var ext = System.getProperty("ext", "txt");
System.out.println(dir);
var directory = Paths.get(dir);
var lines = Files.list(directory).sorted().flatMap(file -> { try{ return Files.lines(file); }catch(Exception ex){ return Stream.of(""); } }).collect(Collectors.toList());
var fileName = directory.getFileName().toString() + "." + ext;
var file = Paths.get(directory.getParent().toString(), fileName);
Files.write(file, lines);
/exit