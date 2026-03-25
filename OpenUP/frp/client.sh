export FRP_TOKEN=
ssh -R :2222:127.0.0.1:22 v0@frp.localhost.direct -p 2200 tcpmux --mux httpconnect --sd organization --user user --token '$FRP_TOKEN'