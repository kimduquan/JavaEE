kubectl create secret generic nextauth --from-literal=NEXTAUTH_SECRET="$(openssl rand -hex 32)"
kubectl create secret generic copilotkit --from-literal=PUBLIC_LICENSE_KEY=""
kubectl create secret generic stripe --from-literal=STRIPE_API_KEY=""
kubectl create configmap stripe --from-literal=PRODUCT_ID=""
helm dependency build EPF-parent-ext
helm upgrade --install epf-parent EPF-parent-ext --wait --debug