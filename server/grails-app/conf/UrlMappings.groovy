class UrlMappings {

	static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }
        "/"(view:'/index')
        "/index"(view:'/index')
        "/authFail"(view:'/authFail')
        "500"(view:'/error')
	}
}
