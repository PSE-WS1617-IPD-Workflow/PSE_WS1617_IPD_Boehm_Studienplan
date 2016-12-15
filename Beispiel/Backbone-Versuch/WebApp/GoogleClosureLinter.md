# Goocle Closure Linter Installieren
Anweisungen gelten für Windows
1. Plugin in Brackets installieren. Das Plugin liegt dann unter
```
AppData\Roaming\Brackets\extensions\user\com.github.mrmeku.closure-linter
```
2. In gjslint.py die erste Zeile ersetzen durch ```#! python2```
3. Python 2.7.X installieren
4. Pfadvariable setzen:
```
Name:  PY_PYTHON
Value: 3
```
5. In closureLinterDomain.js die ```python``` Aufrufe durch ```py -2``` Aufrufe erstzen