import co.yzf.Project
import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_yzf_projectaddMaterial_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/project/addMaterial.gsp" }
public Object run() {
Writer out = getOut()
Writer expressionOut = getExpressionOut()
registerSitemeshPreprocessMode()
printHtmlPart(0)
createTagBody(1, {->
printHtmlPart(1)
invokeTag('captureMeta','sitemesh',5,['gsp_sm_xmlClosingForEmptyTag':(""),'name':("layout"),'content':("main")],-1)
printHtmlPart(2)
createTagBody(2, {->
createClosureForHtmlPart(3, 3)
invokeTag('captureTitle','sitemesh',6,[:],3)
})
invokeTag('wrapTitleTag','sitemesh',6,[:],2)
printHtmlPart(4)
})
invokeTag('captureHead','sitemesh',7,[:],1)
printHtmlPart(4)
createTagBody(1, {->
printHtmlPart(5)
if(true && (flash.message)) {
printHtmlPart(6)
expressionOut.print(flash.message)
printHtmlPart(7)
}
printHtmlPart(8)
createTagBody(2, {->
printHtmlPart(9)
if(true && (pmList == null)) {
printHtmlPart(10)
loop:{
int i = 0
for( type in (allTypeList) ) {
printHtmlPart(11)
expressionOut.print(i)
printHtmlPart(12)
expressionOut.print(type)
printHtmlPart(13)
invokeTag('select','g',37,['id':("mtrs_${i}"),'name':("material"),'from':(co.yzf.Material.findAllByType(type)),'optionKey':("id"),'class':("form-control"),'noSelection':(['': '不需要此类材料'])],-1)
printHtmlPart(13)
invokeTag('select','g',38,['id':("prcs_${i}"),'name':("process"),'from':(allProcs),'optionKey':("id"),'optionValue':("period"),'class':("form-control")],-1)
printHtmlPart(14)
expressionOut.print(i)
printHtmlPart(15)
expressionOut.print(i)
printHtmlPart(16)
expressionOut.print(i)
printHtmlPart(17)
i++
}
}
printHtmlPart(18)
out.print(request.contextPath)
printHtmlPart(19)
expressionOut.print(projectId)
printHtmlPart(20)
expressionOut.print(allTypeList.size())
printHtmlPart(21)
}
else {
printHtmlPart(22)
loop:{
int i = 0
for( pm in (pmList) ) {
printHtmlPart(23)
expressionOut.print(i)
printHtmlPart(24)
invokeTag('render','g',70,['template':("savem"),'model':(['pm':pm,'indx':i])],-1)
printHtmlPart(25)
i++
}
}
printHtmlPart(18)
out.print(request.contextPath)
printHtmlPart(19)
expressionOut.print(projectId)
printHtmlPart(20)
expressionOut.print(pmList.size())
printHtmlPart(21)
}
printHtmlPart(26)
})
invokeTag('form','g',84,['url':([resource:projectInstance, action:'update']),'method':("PUT"),'class':("form-horizontal")],2)
printHtmlPart(27)
expressionOut.print(projectId)
printHtmlPart(28)
expressionOut.print(type)
printHtmlPart(29)
invokeTag('select','g',92,['id':("mtrs_@indx@"),'name':("material"),'from':(allMaterial),'optionKey':("id"),'class':("form-control"),'noSelection':(['': '不需要此类材料'])],-1)
printHtmlPart(29)
invokeTag('select','g',93,['id':("prcs_@indx@"),'name':("process"),'from':(allProcs),'optionKey':("id"),'optionValue':("period"),'class':("form-control")],-1)
printHtmlPart(30)
})
invokeTag('captureBody','sitemesh',101,[:],1)
printHtmlPart(31)
}
public static final Map JSP_TAGS = new HashMap()
protected void init() {
	this.jspTags = JSP_TAGS
}
public static final String CONTENT_TYPE = 'text/html;charset=UTF-8'
public static final long LAST_MODIFIED = 1405350786820L
public static final String EXPRESSION_CODEC = 'html'
public static final String STATIC_CODEC = 'none'
public static final String OUT_CODEC = 'html'
public static final String TAGLIB_CODEC = 'none'
}
