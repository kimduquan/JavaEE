OUTPUT_FILE="$1"
: > "$OUTPUT_FILE"

./export-external-cluster.sh > "$OUTPUT_FILE"