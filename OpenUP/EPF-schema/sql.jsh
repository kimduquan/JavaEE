var directory = Paths.get("", "src/main/resources/META-INF/mysql");
var lines = Files.list(directory).sorted().flatMap(file -> { try{ return Files.lines(file); }catch(Exception ex){ return Stream.of(""); } }).collect(Collectors.toList());
var fileName = directory.getFileName().toString() + ".sql";
var file = Paths.get(directory.getParent().toString(), fileName);
Files.write(file, lines);
/exit