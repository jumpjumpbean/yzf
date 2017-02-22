import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_yzf_templates_procinfo_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/templates/_procinfo.gsp" }
public Object run() {
Writer out = getOut()
Writer expressionOut = getExpressionOut()
registerSitemeshPreprocessMode()
printHtmlPart(0)
invokeTag('set','g',5,['var':("supervisor"),'value':(process?.charger)],-1)
printHtmlPart(1)
invokeTag('set','g',6,['var':("qc"),'value':(process?.project.qc)],-1)
printHtmlPart(2)
expressionOut.print(supervisor?.uname)
printHtmlPart(3)
expressionOut.print(supervisor?.telephone)
printHtmlPart(4)
expressionOut.print(qc?.uname)
printHtmlPart(3)
expressionOut.print(qc?.telephone)
printHtmlPart(5)
expressionOut.print(process?.period)
printHtmlPart(6)
if(true && (material == null || material.size() == 0)) {
printHtmlPart(7)
}
else {
printHtmlPart(8)
loop:{
int i = 0
for( pm in (material) ) {
printHtmlPart(9)
invokeTag('set','g',35,['var':("mtrl"),'value':(pm.material)],-1)
printHtmlPart(10)
expressionOut.print(i+1)
printHtmlPart(11)
expressionOut.print(mtrl?.type)
printHtmlPart(11)
expressionOut.print(mtrl?.name)
printHtmlPart(11)
expressionOut.print(mtrl?.brand)
printHtmlPart(11)
expressionOut.print(mtrl?.modelno)
printHtmlPart(11)
expressionOut.print(pm.cnt)
expressionOut.print(mtrl?.unit)
printHtmlPart(11)
expressionOut.print(pm.memo)
printHtmlPart(12)
i++
}
}
printHtmlPart(13)
}
printHtmlPart(14)
}
public static final Map JSP_TAGS = new HashMap()
protected void init() {
	this.jspTags = JSP_TAGS
}
public static final String CONTENT_TYPE = 'text/html;charset=UTF-8'
public static final long LAST_MODIFIED = 1403456740723L
public static final String EXPRESSION_CODEC = 'html'
public static final String STATIC_CODEC = 'none'
public static final String OUT_CODEC = 'html'
public static final String TAGLIB_CODEC = 'none'
}
