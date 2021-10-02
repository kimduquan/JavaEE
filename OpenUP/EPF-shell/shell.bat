mvn dependency:copy-dependencies -DincludeScope=compile
jshell --class-path ./target/dependency/* shell.jsh