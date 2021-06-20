set output_dir="C://Program Files/pluto-3.1.0/lib"
call mvn dependency:copy -Dartifact=org.glassfish.jersey.core:jersey-client:2.27 -DoutputDirectory=%output_dir%
call mvn dependency:copy -Dartifact=org.glassfish.jersey.core:jersey-common:2.27 -DoutputDirectory=%output_dir%
call mvn dependency:copy -Dartifact=org.glassfish:javax.faces:2.3.9 -DoutputDirectory=%output_dir%