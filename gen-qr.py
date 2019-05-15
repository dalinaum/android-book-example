#!/usr/bin/env python
import qrcode

text = """
01-hello
02-02-button
02-03-edittext
02-04-imageview
03-01-realativelayout
03-02-linearlayout
03-03-framelayout
03-04-gridlayout
03-05-cardview
04-01-toolbar
04-02-customtoolbar
04-04-appbar
04-05-collapsingtoolbar
05-01-databinding
05-02-binding-adapter
06-01-recyclerview
06-02-itemdecoration
06-03-paging
06-04-paging-with-databinding
06-05-listview
07-02-activities
07-03-viewmodel
08-01-fragment
08-02-fragment-with-activity
08-03-fragment-programming
08-04-fragment-replace
09-01-resource
10-01-shared-preferences
10-02-database
11-01-broadcast-receiver
11-02-intent-service
12-01-shared-element
13-01-floating-action-button
13-02-snackbar
14-01-subclass-view
14-02-view-group
15-01-camera
16-01-location
17-01-simple-share
18-01-simple-notification
19-01-authentication
"""

for line in text.splitlines():
    line = line.lstrip()
    if line == "":
        continue
    url = "https://github.com/dalinaum/android-book-example/tree/1st/" + line
    qr_file = line + ".png"
    print(qr_file)
    print(url)
    img = qrcode.make(url)
    img.save(qr_file)
