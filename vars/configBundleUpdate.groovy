// vars/configBundleUpdate.groovy
def call(String nameSpace = "cloudbees-sda") {
  def bundleName = "oc-casc"
  def label = "kubectl"
  def podYaml = libraryResource 'podtemplates/kubectl.yml'
  
  podTemplate(name: 'kubectl', label: label, yaml: podYaml) {
    node(label) {
      checkout scm
      container("kubectl") {
        sh "mkdir -p ${bundleName}"
        sh "cp *.yaml ${bundleName}"
        sh "kubectl cp --namespace ${nameSpace} ${bundleName} cjoc-0:/var/jenkins_home/jcasc-bundles-store/ -c jenkins"
      }
    }
  }
}
