cd dependency
set output_dir="/mnt/C/WSL/pluto-3.1.0/lib"
mvn dependency:copy-dependencies -DincludeScope=compile -DoutputDirectory=%output_dir%
cd ../