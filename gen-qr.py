#!/usr/bin/env python
import qrcode

text = """
01-hello
04-04-appbar
07-04-network-image-viewmodel
12-01-broadcast-receiver
02-02-button
04-05-collapsingtoolbar
08-01-coroutine
12-02-intent-service
02-03-edittext
05-01-databinding
08-02-network-image-coroutine
13-01-shared-element
02-04-imageview
05-02-binding-adapter
08-03-flow-livedata
14-01-floating-action-button
03-01-realativelayout
06-01-recyclerview
09-01-fragment
14-02-snackbar
03-02-linearlayout
06-02-itemdecoration
09-02-fragment-with-activity
15-01-subclass-view
03-03-framelayout
06-03-paging
09-03-fragment-programming
15-02-view-group
03-04-gridlayout
06-04-paging-with-databinding
09-04-fragment-replace
16-01-location
03-05-cardview
06-05-network-image
10-01-resource
17-01-simple-notification
04-01-toolbar
07-02-activities
11-01-shared-preferences
04-02-customtoolbar
07-03-viewmodel
11-02-database
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
