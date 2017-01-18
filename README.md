# PSE_IPD_Boehm_Studienplan

*Use UTF-8 Fileencoding*

## Guidelines for commits:
* __Generally__ refer to the corresponding issues in a commit. (Especially useful if commit message is just e.g. "minor changes")
* __Do not__ use ```git add --all```. We don't want trash files in our repo.
* __Only__ commit _compiling code_. Otherwise you gonna crash others workspace.
* For the client: __Always__ run ```ant test``` before committing and check if there are any errors (if so fix them first)
