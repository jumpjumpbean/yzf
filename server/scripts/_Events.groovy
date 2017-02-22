eventCompileEnd = {
	ant.copy(todir:classesDirPath) {
	  fileset(file:"${basedir}/grails-app/conf/app-config.properties")
	}
}