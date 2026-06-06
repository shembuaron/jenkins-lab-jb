def buildApp() {
    echo "Now building the application..."
}

def deployApp(String branchName) {
    echo "Now deploying the application on branch: ${branchName}..."
}

def cleanup() {
    echo "Cleaning up after build and deployment..."
}
