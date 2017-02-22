import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_yzf_project_savem_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/project/_savem.gsp" }
public Object run() {
Writer out = getOut()
Writer expressionOut = getExpressionOut()
registerSitemeshPreprocessMode()
printHtmlPart(0)
expressionOut.print(pm?.material?.type)
printHtmlPart(1)
expressionOut.print(pm?.material?.brand)
printHtmlPart(2)
expressionOut.print(pm?.material?.modelno)
printHtmlPart(1)
expressionOut.print(pm?.process?.period)
printHtmlPart(1)
expressionOut.print(pm?.cnt)
printHtmlPart(1)
expressionOut.print(pm?.memo)
printHtmlPart(3)
expressionOut.print(pm.id)
printHtmlPart(4)
expressionOut.print(indx)
printHtmlPart(5)
}
public static final Map JSP_TAGS = new HashMap()
protected void init() {
	this.jspTags = JSP_TAGS
}
public static final String CONTENT_TYPE = 'text/html;charset=UTF-8'
public static final long LAST_MODIFIED = 1402830865305L
public static final String EXPRESSION_CODEC = 'html'
public static final String STATIC_CODEC = 'none'
public static final String OUT_CODEC = 'html'
public static final String TAGLIB_CODEC = 'none'
}
