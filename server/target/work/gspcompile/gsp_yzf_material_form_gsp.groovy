import co.yzf.Material
import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_yzf_material_form_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/material/_form.gsp" }
public Object run() {
Writer out = getOut()
Writer expressionOut = getExpressionOut()
registerSitemeshPreprocessMode()
printHtmlPart(0)
expressionOut.print(hasErrors(bean: materialInstance, field: 'name', 'error'))
printHtmlPart(1)
invokeTag('textField','g',7,['name':("name"),'maxlength':("64"),'class':("form-control"),'value':(materialInstance?.name)],-1)
printHtmlPart(2)
expressionOut.print(hasErrors(bean: materialInstance, field: 'type', 'error'))
printHtmlPart(3)
invokeTag('select','g',15,['id':("types"),'name':("types"),'from':(allTypeList),'value':(materialInstance?.type),'class':("form-control"),'noSelection':(['': '其他'])],-1)
printHtmlPart(4)
invokeTag('textField','g',19,['id':("typei"),'name':("typei"),'maxlength':("32"),'class':("form-control"),'value':("")],-1)
printHtmlPart(5)
expressionOut.print(hasErrors(bean: materialInstance, field: 'brand', 'error'))
printHtmlPart(6)
invokeTag('textField','g',28,['name':("brand"),'maxlength':("64"),'class':("form-control"),'value':(materialInstance?.brand)],-1)
printHtmlPart(2)
expressionOut.print(hasErrors(bean: materialInstance, field: 'brand', 'error'))
printHtmlPart(7)
invokeTag('textField','g',35,['name':("modelno"),'maxlength':("63"),'class':("form-control"),'value':(materialInstance?.modelno)],-1)
printHtmlPart(2)
expressionOut.print(hasErrors(bean: materialInstance, field: 'unit', 'error'))
printHtmlPart(8)
invokeTag('textField','g',42,['name':("unit"),'maxlength':("16"),'class':("form-control"),'value':(materialInstance?.unit)],-1)
printHtmlPart(2)
expressionOut.print(hasErrors(bean: materialInstance, field: 'supplier', 'error'))
printHtmlPart(9)
invokeTag('textField','g',49,['name':("supplier"),'maxlength':("64"),'class':("form-control"),'value':(materialInstance?.supplier)],-1)
printHtmlPart(10)
}
public static final Map JSP_TAGS = new HashMap()
protected void init() {
	this.jspTags = JSP_TAGS
}
public static final String CONTENT_TYPE = 'text/html;charset=UTF-8'
public static final long LAST_MODIFIED = 1402241234679L
public static final String EXPRESSION_CODEC = 'html'
public static final String STATIC_CODEC = 'none'
public static final String OUT_CODEC = 'html'
public static final String TAGLIB_CODEC = 'none'
}
