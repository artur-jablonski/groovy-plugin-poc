/**
 * based on https://area-51.blog/2009/11/07/using-groovy-to-generate-java-sources-in-maven/
 */
import groovy.text.GStringTemplateEngine

println "****** Groovy code generation POC starts ******"
// Where to write the classes
File targetDirectory = new File(project.build.directory + '/generated-sources/groovy')

def binding = [
        packageName: "me.artur",
        className  : "GeneratedClass",
        methodName : "generatedMethod",
        result     : "generated result"
]

def output = new GStringTemplateEngine()
        .createTemplate(new File("${project.basedir}/src/main/script/class-template.tpl"))
        .make(binding)


File packageDir = new File(targetDirectory, binding.packageName.replace('.', '/'))
// Now write the source, ensuring the directory exists first
packageDir.mkdirs()

new File(packageDir, binding.className + ".java")
        .withWriter() { it << output }
