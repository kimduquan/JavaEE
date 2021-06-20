set output_dir="C://Program Files/pluto-3.1.0/lib"
call mvn dependency:copy -Dartifact=org.glassfish:javax.faces:2.3.9 -DoutputDirectory=%output_dir%