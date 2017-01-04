# PSE_IPD_Boehm_Studienplan

*Fileencoding in UTF-8*

Please have Telegram open while making changes. Sometimes there are conflicts and to resolve them it is necessary that everyone except one person stops working on a file.


Guidelines for commits:
Please always refer to the corresponding issues in a commit. (Especially useful if commit message is just e.g. "minor changes")

Please don't always use ```git add --all```. We don't want trash files in our repo.
Please only commit compiling code. Otherwise you gonna crash others workspace.

##Tools
Install [Eclipse Papyrus](https://eclipse.org/papyrus/download.html)
###To install Java (Incubation):
Help -> Install Papyrus Additional Comonents -> Designer-JAVA (Incubation)

###Import Project into Eclipse Papyrus:
* Window -> Perspective -> Open Perspective -> Other -> Git
* Change to Git-Perspective -> Add Local Repository -> Select the Folder of this Repository
* File -> Import -> Projects from Git -> Existing local repository -> Next -> Import existing Eclipse projects -> Check Studienplan_PSE -> Finish

###Generate Latex from Javadoc:
* Clone: https://github.com/MatzeB/texdoclet
* Change path of java tools in build.xml
* Use ant jar
* Copy generated jar to source foulde of JavaProject
* run javadoc -docletpath texdoclet.jar -doclet org.wonderly.doclets.TexDoclet *my.cool.package* (whereby *my.cool.package* is the package that java doc should be generated for)
* also see: https://docs.oracle.com/javase/7/docs/technotes/tools/windows/javadoc.html#runningjavadoc

###Set Eclipse to UTF-8
* Go to Window -> Prefernces -> General -> Workspace -> Text file encoding
