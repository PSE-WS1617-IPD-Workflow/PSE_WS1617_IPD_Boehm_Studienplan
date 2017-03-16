## Guidelines for commits:
* Use __UTF-8__ file encoding
* __Generally__ refer to the corresponding issues in a commit. (Especially useful if commit message is just e.g. "minor changes")
* __Do not__ add trash files to our repository (avoid ```git add --all```).
* __Only__ commit _compiling code_.
* For the WebApp Client: __Always__ run ```ant test``` before committing and check if there are any errors (if so fix them first)
* For the server: __Verify__ code functionality with ```mvn verify``` before commiting
## Guidelines for Issues:
* If you are sure whether it is a client or server issue, please add the corresponding labels (```scope: client``` or ```scope: server```)
* Please add a label if the issue is a bug report (```type: bug```) or a suggestion (```type: enhancement```)
* If an important functionality of the product is not working, you may add the label ```priority: critical``` to the issue
