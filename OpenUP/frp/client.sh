export FRP_TOKEN=
ssh -R :80:127.0.0.1:8080 v0@frp.localhost.direct -p 2200 tcpmux --mux httpconnect --token '$FRP_TOKEN'