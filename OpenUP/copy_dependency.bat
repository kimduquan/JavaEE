cd dependency
set output_dir="C://Program Files/pluto-3.1.0/lib"
call mvn dependency:copy-dependencies -DincludeScope=compile -DoutputDirectory=%output_dir%
cd ../