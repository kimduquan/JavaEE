# https://github.com/rook/rook/blob/v1.21.3/deploy/examples/create-external-cluster-resources.py
# https://github.com/rook/rook/blob/v1.21.3/deploy/examples/import-external-cluster.sh
# https://github.com/rook/rook/blob/v1.21.3/deploy/charts/rook-ceph-cluster/values-external.yaml
OUTPUT_FILE="$1"
if [ -z "$OUTPUT_FILE" ]; then
    OUTPUT_FILE="export-env.sh"
fi
: > "$OUTPUT_FILE"

./export-external-cluster.sh > "$OUTPUT_FILE"