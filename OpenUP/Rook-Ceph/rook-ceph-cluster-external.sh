# https://github.com/rook/rook/blob/v1.21.3/deploy/examples/create-external-cluster-resources.py
OUTPUT_FILE="$1"
if [ -z "$OUTPUT_FILE" ]; then
    OUTPUT_FILE="export-env.sh"
fi
: > "$OUTPUT_FILE"

./export-external-cluster.sh > "$OUTPUT_FILE"