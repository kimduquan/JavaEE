OUTPUT_FILE="$1"
if [ -z "$OUTPUT_FILE" ]; then
    OUTPUT_FILE="export-env.sh"
fi
: > "$OUTPUT_FILE"

./export-external-cluster.sh > "$OUTPUT_FILE"