var userHome = Paths.get(System.getProperty("user.home"));
var dbFile = userHome.resolve("EPF_test.mv.db");
var traceFile = userHome.resolve("EPF_test.trace.db");
Files.deleteIfExists(dbFile);
Files.deleteIfExists(traceFile);
/exit