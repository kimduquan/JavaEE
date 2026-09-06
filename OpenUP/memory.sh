INTERVAL=3

while true; do
    # Get memory values in MB from `free -m`
    read -r TOTAL USED FREE SHARED BUFF_CACHE AVAILABLE < <(
        free -m | awk '/^Mem:/ {
            print $2, $3, $4, $5, $6, $7
        }'
    )

    echo "$(date '+%Y-%m-%d %H:%M:%S') | Total: ${TOTAL}MB | Used: ${USED}MB | Free: ${FREE}MB | Buff/Cache: ${BUFF_CACHE}MB | Available: ${AVAILABLE}MB"

    # Clear buffer/cache when Buff/Cache > Free
    if [ "$BUFF_CACHE" -gt "$FREE" ]; then
        echo "Buffer/cache (${BUFF_CACHE}MB) > Free (${FREE}MB). Clearing..."

        sync && sudo sysctl -w vm.drop_caches=3

        echo "Buffer/cache cleared."
    fi

    sleep "$INTERVAL"
done