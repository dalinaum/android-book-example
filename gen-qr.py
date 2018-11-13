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
