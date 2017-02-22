import co.yzf.Project
import org.codehaus.groovy.grails.plugins.metadata.GrailsPlugin
import org.codehaus.groovy.grails.web.pages.GroovyPage
import org.codehaus.groovy.grails.web.taglib.*
import org.codehaus.groovy.grails.web.taglib.exceptions.GrailsTagException
import org.springframework.web.util.*
import grails.util.GrailsUtil

class gsp_yzf_projectshow_gsp extends GroovyPage {
public String getGroovyPageFileName() { "/WEB-INF/grails-app/views/project/show.gsp" }
public Object run() {
Writer out = getOut()
Writer expressionOut = getExpressionOut()
registerSitemeshPreprocessMode()
printHtmlPart(0)
createTagBody(1, {->
printHtmlPart(1)
invokeTag('captureMeta','sitemesh',5,['gsp_sm_xmlClosingForEmptyTag':(""),'name':("layout"),'content':("main")],-1)
printHtmlPart(1)
createTagBody(2, {->
createTagBody(3, {->
invokeTag('fieldValue','g',6,['bean':(projectInstance),'field':("address")],-1)
})
invokeTag('captureTitle','sitemesh',6,[:],3)
})
invokeTag('wrapTitleTag','sitemesh',6,[:],2)
printHtmlPart(2)
expressionOut.print(resource(dir: 'css', file: 'morris.css'))
printHtmlPart(3)
})
invokeTag('captureHead','sitemesh',8,[:],1)
printHtmlPart(4)
createTagBody(1, {->
printHtmlPart(5)
if(true && (flash.message)) {
printHtmlPart(6)
expressionOut.print(flash.message)
printHtmlPart(7)
}
printHtmlPart(8)
if(true && (projectInstance?.user)) {
printHtmlPart(9)
expressionOut.print(projectInstance?.user?.encodeAsHTML())
printHtmlPart(10)
}
printHtmlPart(11)
invokeTag('fieldValue','g',31,['bean':(projectInstance),'field':("customer")],-1)
printHtmlPart(12)
invokeTag('fieldValue','g',36,['bean':(projectInstance),'field':("contractNo")],-1)
printHtmlPart(13)
invokeTag('fieldValue','g',41,['bean':(projectInstance),'field':("address")],-1)
printHtmlPart(14)
invokeTag('formatDate','g',46,['date':(projectInstance?.planStartDate),'format':("yyyy-MM-dd")],-1)
printHtmlPart(15)
invokeTag('formatDate','g',51,['date':(projectInstance?.planEndDate),'format':("yyyy-MM-dd")],-1)
printHtmlPart(16)
invokeTag('formatDate','g',56,['date':(projectInstance?.realStartDate),'format':("yyyy-MM-dd")],-1)
printHtmlPart(17)
invokeTag('formatDate','g',61,['date':(projectInstance?.realEndDate),'format':("yyyy-MM-dd")],-1)
printHtmlPart(18)
expressionOut.print(projectInstance?.responser?.uname)
printHtmlPart(19)
expressionOut.print(projectInstance?.qc?.uname)
printHtmlPart(20)
invokeTag('fieldValue','g',75,['bean':(projectInstance),'field':("totalCost")],-1)
printHtmlPart(21)
invokeTag('fieldValue','g',80,['bean':(projectInstance),'field':("imprest")],-1)
printHtmlPart(22)
invokeTag('fieldValue','g',85,['bean':(projectInstance),'field':("payment")],-1)
printHtmlPart(23)
if(true && (projectInstance.status == 1)) {
printHtmlPart(24)
}
else if(true && (projectInstance.status == 2)) {
printHtmlPart(25)
}
else {
printHtmlPart(26)
}
printHtmlPart(27)
createTagBody(2, {->
printHtmlPart(28)
createClosureForHtmlPart(29, 3)
invokeTag('link','g',105,['class':("btn btn-primary"),'action':("edit"),'resource':(projectInstance)],3)
printHtmlPart(28)
if(true && (projectInstance?.status == 1)) {
printHtmlPart(30)
createClosureForHtmlPart(31, 4)
invokeTag('link','g',107,['class':("btn btn-success"),'controller':("process"),'action':("schedule"),'id':(projectInstance?.id)],4)
printHtmlPart(28)
}
printHtmlPart(32)
out.print(request.contextPath)
printHtmlPart(33)
expressionOut.print(projectInstance?.id)
printHtmlPart(34)
})
invokeTag('ifAnyGranted','sec',110,['roles':("ROLE_ADMIN,ROLE_SUPER")],2)
printHtmlPart(35)
out.print(request.contextPath)
printHtmlPart(36)
expressionOut.print(projectInstance?.id)
printHtmlPart(37)
if(true && (projectInstance.status == 1)) {
printHtmlPart(38)
}
else {
printHtmlPart(39)
loop:{
int j = 0
for( process in (processList) ) {
printHtmlPart(30)
if(true && (process.status == 2)) {
printHtmlPart(40)
invokeTag('formatDate','g',132,['date':(process?.realStartDate),'format':("M/d")],-1)
printHtmlPart(41)
expressionOut.print(process.id)
printHtmlPart(42)
expressionOut.print(process.period.name)
printHtmlPart(43)
}
else if(true && (process.status == 3)) {
printHtmlPart(44)
invokeTag('formatDate','g',138,['date':(process?.realStartDate),'format':("M/d")],-1)
printHtmlPart(41)
expressionOut.print(process.id)
printHtmlPart(42)
expressionOut.print(process.period.name)
printHtmlPart(43)
}
else {
printHtmlPart(45)
expressionOut.print(process.id)
printHtmlPart(42)
expressionOut.print(process.period.name)
printHtmlPart(46)
}
printHtmlPart(28)
j++
}
}
printHtmlPart(47)
loop:{
int j = 0
for( process in (processList) ) {
printHtmlPart(30)
if(true && (process.status == 2)) {
printHtmlPart(48)
expressionOut.print(process.id)
printHtmlPart(49)
invokeTag('set','g',154,['var':("allProcStep"),'value':(co.yzf.ProcessStep.findAllByProcess(process))],-1)
printHtmlPart(50)
if(true && (process.period.hasMaterial == 1)) {
printHtmlPart(51)
loop:{
int i = 0
for( procstep in (allProcStep) ) {
printHtmlPart(52)
invokeTag('set','g',157,['var':("stepId"),'value':(procstep.step.id)],-1)
printHtmlPart(52)
if(true && (procstep.status == 3)) {
printHtmlPart(53)
if(true && (stepId == 5)) {
printHtmlPart(54)
expressionOut.print(procstep?.step?.name)
printHtmlPart(55)
}
else if(true && (stepId == 1)) {
printHtmlPart(56)
expressionOut.print(procstep?.step?.name)
printHtmlPart(55)
}
else {
printHtmlPart(57)
expressionOut.print(procstep?.step?.name)
printHtmlPart(55)
}
printHtmlPart(52)
}
else {
printHtmlPart(53)
if(true && (stepId == 1)) {
printHtmlPart(58)
if(true && (curUser.id == process.charger?.id)) {
printHtmlPart(59)
expressionOut.print(stepId)
printHtmlPart(60)
expressionOut.print(procstep.sgroup)
printHtmlPart(61)
expressionOut.print(procstep.status)
printHtmlPart(62)
out.print(request.contextPath)
printHtmlPart(63)
expressionOut.print(procstep.id)
printHtmlPart(64)
expressionOut.print(procstep?.step?.name)
printHtmlPart(65)
}
else {
printHtmlPart(66)
createTagBody(10, {->
printHtmlPart(67)
expressionOut.print(stepId)
printHtmlPart(60)
expressionOut.print(procstep.sgroup)
printHtmlPart(61)
expressionOut.print(procstep.status)
printHtmlPart(62)
out.print(request.contextPath)
printHtmlPart(63)
expressionOut.print(procstep.id)
printHtmlPart(64)
expressionOut.print(procstep?.step?.name)
printHtmlPart(68)
})
invokeTag('ifAnyGranted','sec',177,['roles':("ROLE_ADMIN,ROLE_SUPER")],10)
printHtmlPart(66)
createTagBody(10, {->
printHtmlPart(69)
expressionOut.print(procstep?.step?.name)
printHtmlPart(68)
})
invokeTag('ifNotGranted','sec',180,['roles':("ROLE_ADMIN,ROLE_SUPER")],10)
printHtmlPart(58)
}
printHtmlPart(53)
}
printHtmlPart(53)
if(true && (stepId == 2)) {
printHtmlPart(58)
if(true && (allProcStep[i-1].status == 3)) {
printHtmlPart(66)
if(true && (curUser.id == projectInstance.qc?.id)) {
printHtmlPart(70)
expressionOut.print(stepId)
printHtmlPart(60)
expressionOut.print(procstep.sgroup)
printHtmlPart(61)
expressionOut.print(procstep.status)
printHtmlPart(62)
out.print(request.contextPath)
printHtmlPart(71)
expressionOut.print(procstep.id)
printHtmlPart(64)
expressionOut.print(procstep?.step?.name)
printHtmlPart(68)
}
else {
printHtmlPart(72)
createTagBody(11, {->
printHtmlPart(73)
expressionOut.print(stepId)
printHtmlPart(60)
expressionOut.print(procstep.sgroup)
printHtmlPart(61)
expressionOut.print(procstep.status)
printHtmlPart(62)
out.print(request.contextPath)
printHtmlPart(71)
expressionOut.print(procstep.id)
printHtmlPart(64)
expressionOut.print(procstep?.step?.name)
printHtmlPart(74)
})
invokeTag('ifAnyGranted','sec',191,['roles':("ROLE_ADMIN,ROLE_SUPER")],11)
printHtmlPart(72)
createTagBody(11, {->
printHtmlPart(75)
expressionOut.print(procstep?.step?.name)
printHtmlPart(74)
})
invokeTag('ifNotGranted','sec',194,['roles':("ROLE_ADMIN,ROLE_SUPER")],11)
printHtmlPart(66)
}
printHtmlPart(58)
}
else {
printHtmlPart(76)
expressionOut.print(procstep?.step?.name)
printHtmlPart(65)
}
printHtmlPart(53)
}
printHtmlPart(53)
if(true && (stepId == 3)) {
printHtmlPart(58)
if(true && (allProcStep[i-1].status == 3)) {
printHtmlPart(66)
createTagBody(10, {->
printHtmlPart(70)
expressionOut.print(stepId)
printHtmlPart(60)
expressionOut.print(procstep.sgroup)
printHtmlPart(61)
expressionOut.print(procstep.status)
printHtmlPart(62)
out.print(request.contextPath)
printHtmlPart(77)
expressionOut.print(procstep.id)
printHtmlPart(64)
expressionOut.print(procstep?.step?.name)
printHtmlPart(68)
})
invokeTag('ifAnyGranted','sec',205,['roles':("ROLE_ADMIN,ROLE_SUPER")],10)
printHtmlPart(66)
createTagBody(10, {->
printHtmlPart(78)
expressionOut.print(procstep?.step?.name)
printHtmlPart(68)
})
invokeTag('ifNotGranted','sec',208,['roles':("ROLE_ADMIN,ROLE_SUPER")],10)
printHtmlPart(58)
}
else {
printHtmlPart(76)
expressionOut.print(procstep?.step?.name)
printHtmlPart(65)
}
printHtmlPart(53)
}
printHtmlPart(53)
if(true && (stepId == 4)) {
printHtmlPart(58)
if(true && (allProcStep[i-1].status == 3)) {
printHtmlPart(66)
if(true && (curUser.id == projectInstance.user?.id)) {
printHtmlPart(70)
expressionOut.print(stepId)
printHtmlPart(60)
expressionOut.print(procstep.sgroup)
printHtmlPart(61)
expressionOut.print(procstep.status)
printHtmlPart(62)
out.print(request.contextPath)
printHtmlPart(79)
expressionOut.print(procstep.id)
printHtmlPart(64)
expressionOut.print(procstep?.step?.name)
printHtmlPart(68)
}
else {
printHtmlPart(72)
createTagBody(11, {->
printHtmlPart(73)
expressionOut.print(stepId)
printHtmlPart(60)
expressionOut.print(procstep.sgroup)
printHtmlPart(61)
expressionOut.print(procstep.status)
printHtmlPart(62)
out.print(request.contextPath)
printHtmlPart(79)
expressionOut.print(procstep.id)
printHtmlPart(64)
expressionOut.print(procstep?.step?.name)
printHtmlPart(74)
})
invokeTag('ifAnyGranted','sec',222,['roles':("ROLE_ADMIN,ROLE_SUPER")],11)
printHtmlPart(72)
createTagBody(11, {->
printHtmlPart(75)
expressionOut.print(procstep?.step?.name)
printHtmlPart(74)
})
invokeTag('ifNotGranted','sec',225,['roles':("ROLE_ADMIN,ROLE_SUPER")],11)
printHtmlPart(66)
}
printHtmlPart(58)
}
else {
printHtmlPart(76)
expressionOut.print(procstep?.step?.name)
printHtmlPart(65)
}
printHtmlPart(53)
}
printHtmlPart(53)
if(true && (stepId == 5)) {
printHtmlPart(58)
if(true && (allProcStep[i-1].status == 3)) {
printHtmlPart(66)
if(true && (curUser.id == process.charger?.id)) {
printHtmlPart(80)
expressionOut.print(stepId)
printHtmlPart(60)
expressionOut.print(procstep.sgroup)
printHtmlPart(61)
expressionOut.print(procstep.status)
printHtmlPart(62)
out.print(request.contextPath)
printHtmlPart(63)
expressionOut.print(procstep.id)
printHtmlPart(64)
expressionOut.print(procstep?.step?.name)
printHtmlPart(68)
}
else {
printHtmlPart(72)
createTagBody(11, {->
printHtmlPart(81)
expressionOut.print(stepId)
printHtmlPart(60)
expressionOut.print(procstep.sgroup)
printHtmlPart(61)
expressionOut.print(procstep.status)
printHtmlPart(62)
out.print(request.contextPath)
printHtmlPart(63)
expressionOut.print(procstep.id)
printHtmlPart(64)
expressionOut.print(procstep?.step?.name)
printHtmlPart(74)
})
invokeTag('ifAnyGranted','sec',240,['roles':("ROLE_ADMIN,ROLE_SUPER")],11)
printHtmlPart(72)
createTagBody(11, {->
printHtmlPart(82)
expressionOut.print(procstep?.step?.name)
printHtmlPart(74)
})
invokeTag('ifNotGranted','sec',243,['roles':("ROLE_ADMIN,ROLE_SUPER")],11)
printHtmlPart(66)
}
printHtmlPart(58)
}
else {
printHtmlPart(83)
expressionOut.print(procstep?.step?.name)
printHtmlPart(65)
}
printHtmlPart(53)
}
printHtmlPart(53)
if(true && (stepId == 6)) {
printHtmlPart(58)
if(true && (allProcStep[i-1].status == 3)) {
printHtmlPart(66)
if(true && (curUser.id == projectInstance.qc?.id)) {
printHtmlPart(70)
expressionOut.print(stepId)
printHtmlPart(60)
expressionOut.print(procstep.sgroup)
printHtmlPart(61)
expressionOut.print(procstep.status)
printHtmlPart(62)
out.print(request.contextPath)
printHtmlPart(71)
expressionOut.print(procstep.id)
printHtmlPart(64)
expressionOut.print(procstep?.step?.name)
printHtmlPart(68)
}
else {
printHtmlPart(72)
createTagBody(11, {->
printHtmlPart(73)
expressionOut.print(stepId)
printHtmlPart(60)
expressionOut.print(procstep.sgroup)
printHtmlPart(61)
expressionOut.print(procstep.status)
printHtmlPart(62)
out.print(request.contextPath)
printHtmlPart(71)
expressionOut.print(procstep.id)
printHtmlPart(64)
expressionOut.print(procstep?.step?.name)
printHtmlPart(74)
})
invokeTag('ifAnyGranted','sec',258,['roles':("ROLE_ADMIN,ROLE_SUPER")],11)
printHtmlPart(72)
createTagBody(11, {->
printHtmlPart(75)
expressionOut.print(procstep?.step?.name)
printHtmlPart(74)
})
invokeTag('ifNotGranted','sec',261,['roles':("ROLE_ADMIN,ROLE_SUPER")],11)
printHtmlPart(66)
}
printHtmlPart(58)
}
else {
printHtmlPart(76)
expressionOut.print(procstep?.step?.name)
printHtmlPart(65)
}
printHtmlPart(53)
}
printHtmlPart(53)
if(true && (stepId == 7)) {
printHtmlPart(58)
if(true && (allProcStep[i-1].status == 3)) {
printHtmlPart(66)
createTagBody(10, {->
printHtmlPart(70)
expressionOut.print(stepId)
printHtmlPart(60)
expressionOut.print(procstep.sgroup)
printHtmlPart(61)
expressionOut.print(procstep.status)
printHtmlPart(62)
out.print(request.contextPath)
printHtmlPart(77)
expressionOut.print(procstep.id)
printHtmlPart(64)
expressionOut.print(procstep?.step?.name)
printHtmlPart(68)
})
invokeTag('ifAnyGranted','sec',272,['roles':("ROLE_ADMIN,ROLE_SUPER")],10)
printHtmlPart(66)
createTagBody(10, {->
printHtmlPart(78)
expressionOut.print(procstep?.step?.name)
printHtmlPart(68)
})
invokeTag('ifNotGranted','sec',275,['roles':("ROLE_ADMIN,ROLE_SUPER")],10)
printHtmlPart(58)
}
else {
printHtmlPart(76)
expressionOut.print(procstep?.step?.name)
printHtmlPart(65)
}
printHtmlPart(53)
}
printHtmlPart(53)
if(true && (stepId == 8)) {
printHtmlPart(58)
if(true && (allProcStep[i-1].status == 3)) {
printHtmlPart(66)
if(true && (curUser.id == projectInstance.user?.id)) {
printHtmlPart(70)
expressionOut.print(stepId)
printHtmlPart(60)
expressionOut.print(procstep.sgroup)
printHtmlPart(61)
expressionOut.print(procstep.status)
printHtmlPart(62)
out.print(request.contextPath)
printHtmlPart(79)
expressionOut.print(procstep.id)
printHtmlPart(64)
expressionOut.print(procstep?.step?.name)
printHtmlPart(68)
}
else {
printHtmlPart(72)
createTagBody(11, {->
printHtmlPart(73)
expressionOut.print(stepId)
printHtmlPart(60)
expressionOut.print(procstep.sgroup)
printHtmlPart(61)
expressionOut.print(procstep.status)
printHtmlPart(62)
out.print(request.contextPath)
printHtmlPart(79)
expressionOut.print(procstep.id)
printHtmlPart(64)
expressionOut.print(procstep?.step?.name)
printHtmlPart(74)
})
invokeTag('ifAnyGranted','sec',289,['roles':("ROLE_ADMIN,ROLE_SUPER")],11)
printHtmlPart(72)
createTagBody(11, {->
printHtmlPart(75)
expressionOut.print(procstep?.step?.name)
printHtmlPart(74)
})
invokeTag('ifNotGranted','sec',292,['roles':("ROLE_ADMIN,ROLE_SUPER")],11)
printHtmlPart(66)
}
printHtmlPart(58)
}
else {
printHtmlPart(76)
expressionOut.print(procstep?.step?.name)
printHtmlPart(65)
}
printHtmlPart(53)
}
printHtmlPart(52)
}
printHtmlPart(51)
i++
}
}
printHtmlPart(50)
}
else {
printHtmlPart(51)
loop:{
int i = 0
for( procstep in (allProcStep) ) {
printHtmlPart(52)
invokeTag('set','g',304,['var':("stepId"),'value':(procstep.step.id)],-1)
printHtmlPart(52)
if(true && (procstep.status == 3)) {
printHtmlPart(84)
if(true && (stepId == 9)) {
printHtmlPart(85)
}
printHtmlPart(86)
expressionOut.print(procstep?.step?.name)
printHtmlPart(87)
}
else {
printHtmlPart(53)
if(true && (stepId == 9)) {
printHtmlPart(58)
if(true && (curUser.id == process.charger?.id)) {
printHtmlPart(88)
expressionOut.print(stepId)
printHtmlPart(60)
expressionOut.print(procstep.sgroup)
printHtmlPart(61)
expressionOut.print(procstep.status)
printHtmlPart(62)
out.print(request.contextPath)
printHtmlPart(63)
expressionOut.print(procstep.id)
printHtmlPart(64)
expressionOut.print(procstep?.step?.name)
printHtmlPart(65)
}
else {
printHtmlPart(66)
createTagBody(10, {->
printHtmlPart(80)
expressionOut.print(stepId)
printHtmlPart(60)
expressionOut.print(procstep.sgroup)
printHtmlPart(61)
expressionOut.print(procstep.status)
printHtmlPart(62)
out.print(request.contextPath)
printHtmlPart(63)
expressionOut.print(procstep.id)
printHtmlPart(64)
expressionOut.print(procstep?.step?.name)
printHtmlPart(68)
})
invokeTag('ifAnyGranted','sec',316,['roles':("ROLE_ADMIN,ROLE_SUPER")],10)
printHtmlPart(66)
createTagBody(10, {->
printHtmlPart(89)
expressionOut.print(procstep?.step?.name)
printHtmlPart(68)
})
invokeTag('ifNotGranted','sec',319,['roles':("ROLE_ADMIN,ROLE_SUPER")],10)
printHtmlPart(58)
}
printHtmlPart(53)
}
printHtmlPart(53)
if(true && (stepId == 10)) {
printHtmlPart(58)
if(true && (allProcStep[i-1].status == 3)) {
printHtmlPart(66)
if(true && (curUser.id == projectInstance.qc?.id)) {
printHtmlPart(70)
expressionOut.print(stepId)
printHtmlPart(60)
expressionOut.print(procstep.sgroup)
printHtmlPart(61)
expressionOut.print(procstep.status)
printHtmlPart(62)
out.print(request.contextPath)
printHtmlPart(71)
expressionOut.print(procstep.id)
printHtmlPart(64)
expressionOut.print(procstep?.step?.name)
printHtmlPart(68)
}
else {
printHtmlPart(72)
createTagBody(11, {->
printHtmlPart(73)
expressionOut.print(stepId)
printHtmlPart(60)
expressionOut.print(procstep.sgroup)
printHtmlPart(61)
expressionOut.print(procstep.status)
printHtmlPart(62)
out.print(request.contextPath)
printHtmlPart(71)
expressionOut.print(procstep.id)
printHtmlPart(64)
expressionOut.print(procstep?.step?.name)
printHtmlPart(74)
})
invokeTag('ifAnyGranted','sec',330,['roles':("ROLE_ADMIN,ROLE_SUPER")],11)
printHtmlPart(72)
createTagBody(11, {->
printHtmlPart(75)
expressionOut.print(procstep?.step?.name)
printHtmlPart(74)
})
invokeTag('ifNotGranted','sec',333,['roles':("ROLE_ADMIN,ROLE_SUPER")],11)
printHtmlPart(66)
}
printHtmlPart(58)
}
else {
printHtmlPart(76)
expressionOut.print(procstep?.step?.name)
printHtmlPart(65)
}
printHtmlPart(53)
}
printHtmlPart(53)
if(true && (stepId == 11)) {
printHtmlPart(58)
if(true && (allProcStep[i-1].status == 3)) {
printHtmlPart(66)
createTagBody(10, {->
printHtmlPart(70)
expressionOut.print(stepId)
printHtmlPart(60)
expressionOut.print(procstep.sgroup)
printHtmlPart(61)
expressionOut.print(procstep.status)
printHtmlPart(90)
out.print(request.contextPath)
printHtmlPart(77)
expressionOut.print(procstep.id)
printHtmlPart(64)
expressionOut.print(procstep?.step?.name)
printHtmlPart(68)
})
invokeTag('ifAnyGranted','sec',344,['roles':("ROLE_ADMIN,ROLE_SUPER")],10)
printHtmlPart(66)
createTagBody(10, {->
printHtmlPart(78)
expressionOut.print(procstep?.step?.name)
printHtmlPart(68)
})
invokeTag('ifNotGranted','sec',347,['roles':("ROLE_ADMIN,ROLE_SUPER")],10)
printHtmlPart(58)
}
else {
printHtmlPart(76)
expressionOut.print(procstep?.step?.name)
printHtmlPart(65)
}
printHtmlPart(53)
}
printHtmlPart(53)
if(true && (stepId == 12)) {
printHtmlPart(58)
if(true && (allProcStep[i-1].status == 3)) {
printHtmlPart(66)
if(true && (curUser.id == projectInstance.user?.id)) {
printHtmlPart(70)
expressionOut.print(stepId)
printHtmlPart(60)
expressionOut.print(procstep.sgroup)
printHtmlPart(61)
expressionOut.print(procstep.status)
printHtmlPart(62)
out.print(request.contextPath)
printHtmlPart(79)
expressionOut.print(procstep.id)
printHtmlPart(64)
expressionOut.print(procstep?.step?.name)
printHtmlPart(68)
}
else {
printHtmlPart(72)
createTagBody(11, {->
printHtmlPart(73)
expressionOut.print(stepId)
printHtmlPart(60)
expressionOut.print(procstep.sgroup)
printHtmlPart(61)
expressionOut.print(procstep.status)
printHtmlPart(62)
out.print(request.contextPath)
printHtmlPart(79)
expressionOut.print(procstep.id)
printHtmlPart(64)
expressionOut.print(procstep?.step?.name)
printHtmlPart(74)
})
invokeTag('ifAnyGranted','sec',361,['roles':("ROLE_ADMIN,ROLE_SUPER")],11)
printHtmlPart(72)
createTagBody(11, {->
printHtmlPart(75)
expressionOut.print(procstep?.step?.name)
printHtmlPart(74)
})
invokeTag('ifNotGranted','sec',364,['roles':("ROLE_ADMIN,ROLE_SUPER")],11)
printHtmlPart(66)
}
printHtmlPart(58)
}
else {
printHtmlPart(76)
expressionOut.print(procstep?.step?.name)
printHtmlPart(65)
}
printHtmlPart(53)
}
printHtmlPart(52)
}
printHtmlPart(51)
i++
}
}
printHtmlPart(50)
}
printHtmlPart(91)
createTagBody(5, {->
printHtmlPart(52)
if(true && (process.status < 8)) {
printHtmlPart(92)
expressionOut.print(process.id)
printHtmlPart(93)
expressionOut.print(process?.charger?.id)
printHtmlPart(94)
}
printHtmlPart(51)
})
invokeTag('ifAnyGranted','sec',381,['roles':("ROLE_ADMIN,ROLE_SUPER")],5)
printHtmlPart(95)
}
else if(true && (process.status == 3)) {
printHtmlPart(96)
expressionOut.print(process.id)
printHtmlPart(97)
expressionOut.print(process.memo)
printHtmlPart(98)
out.print(request.contextPath)
printHtmlPart(99)
expressionOut.print(process.id)
printHtmlPart(100)
}
else {
printHtmlPart(96)
expressionOut.print(process.id)
printHtmlPart(101)
}
printHtmlPart(28)
j++
}
}
printHtmlPart(102)
expressionOut.print(projectInstance?.id)
printHtmlPart(103)
}
printHtmlPart(104)
if(true && (projectInstance.status != 1)) {
printHtmlPart(105)
expressionOut.print(resource(dir: 'img', file: 'uploadify.swf'))
printHtmlPart(106)
expressionOut.print(projectInstance?.id)
printHtmlPart(107)
out.print(request.contextPath)
printHtmlPart(108)
out.print(request.contextPath)
printHtmlPart(109)
out.print(request.contextPath)
printHtmlPart(110)
}
printHtmlPart(4)
})
invokeTag('captureBody','sitemesh',499,[:],1)
printHtmlPart(111)
}
public static final Map JSP_TAGS = new HashMap()
protected void init() {
	this.jspTags = JSP_TAGS
}
public static final String CONTENT_TYPE = 'text/html;charset=UTF-8'
public static final long LAST_MODIFIED = 1405181970651L
public static final String EXPRESSION_CODEC = 'html'
public static final String STATIC_CODEC = 'none'
public static final String OUT_CODEC = 'html'
public static final String TAGLIB_CODEC = 'none'
}
