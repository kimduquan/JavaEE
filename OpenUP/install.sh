kubectl create secret generic nextauth --from-literal=NEXTAUTH_SECRET="$(openssl rand -hex 32)"
helm dependency build EPF-parent-ext
helm install epf-parent EPF-parent-ext --wait