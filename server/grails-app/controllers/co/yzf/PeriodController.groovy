package co.yzf

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class PeriodController {
    static allowedMethods = [save: "POST", update: "PUT", delete: "GET"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Period.list(params), model: [periodInstanceCount: Period.count()]
    }

    def show(Period periodInstance) {
        respond periodInstance
    }

    def create() {
        respond new Period(params)
    }

    @Transactional
    def save(Period periodInstance) {
        if (periodInstance == null) {
            notFound()
            return
        }
        if (periodInstance.hasErrors()) {
            respond periodInstance.errors, view: 'create'
            return
        }
        periodInstance.save flush: true
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'periodInstance.label', default: 'Period'), periodInstance.name])
                redirect action: "index", method: "GET"
            }
            '*' { respond periodInstance, [status: CREATED] }
        }
    }

    def edit(Period periodInstance) {
        respond periodInstance
    }

    @Transactional
    def update(Period periodInstance) {
        if (periodInstance == null) {
            notFound()
            return
        }
        if (periodInstance.hasErrors()) {
            respond periodInstance.errors, view: 'edit'
            return
        }
        periodInstance.save flush: true
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Period.label', default: 'Period'), periodInstance.name])
                redirect action: "index", method: "GET"
            }
            '*' { respond periodInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Period periodInstance) {
        if (periodInstance == null) {
            notFound()
            return
        }
        periodInstance.delete flush: true
        redirect action: "index", method: "GET"
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'periodInstance.label', default: 'Period'), params.id])
                redirect action: "index", method: "GET"
            }
            '*' { render status: NOT_FOUND }
        }
    }
}
