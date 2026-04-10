kubectl create secret generic nextauth --from-literal=NEXTAUTH_SECRET="$(openssl rand -hex 32)"
kubectl create secret generic copilotkit --from-literal=PUBLIC_LICENSE_KEY=""
helm dependency build EPF-parent-ext
helm upgrade --install epf-parent EPF-parent-ext --wait