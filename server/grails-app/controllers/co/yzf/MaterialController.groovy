package co.yzf

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class MaterialController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "GET"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Material.list(params), model: [materialInstanceCount: Material.count()]
    }

    def show(Material materialInstance) {
        respond materialInstance
    }

    def create() {
        respond new Material(params), model: [allTypeList: Material.executeQuery("select distinct type from Material")]
    }

    @Transactional
    def save(Material materialInstance) {
        if (materialInstance == null) {
            notFound()
            return
        }

        if (materialInstance.hasErrors()) {
            respond materialInstance.errors, view: 'create'
            return
        }

        materialInstance.save flush: true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'materialInstance.label', default: 'Material'), materialInstance.name])
                redirect action: "index", method: "GET"
            }
            '*' { respond materialInstance, [status: CREATED] }
        }
    }

    def edit(Material materialInstance) {
        respond materialInstance, model: [allTypeList: Material.executeQuery("select distinct type from Material")]
    }

    @Transactional
    def update(Material materialInstance) {
        if (materialInstance == null) {
            notFound()
            return
        }

        if (materialInstance.hasErrors()) {
            respond materialInstance.errors, view: 'edit'
            return
        }
        materialInstance.save flush: true
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Material.label', default: 'Material'), materialInstance.id])
                redirect action: "index", method: "GET"
            }
            '*' { respond materialInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Material materialInstance) {
        if (materialInstance == null) {
            notFound()
            return
        }
        materialInstance.delete flush: true
        redirect action: "index", method: "GET"
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'materialInstance.label', default: 'Material'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}
