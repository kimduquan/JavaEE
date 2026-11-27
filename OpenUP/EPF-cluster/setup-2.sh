. ../env.sh ../
cd ../

cd Supavisor
./supavisor.sh
cd tenants
./tenants.sh
cd ../../

cd frp/frpc
export DEPLOYMENT=production
./frpc.sh
export DEPLOYMENT=staging
./frpc.sh
export DEPLOYMENT=dev
./frpc.sh
cd ../../
