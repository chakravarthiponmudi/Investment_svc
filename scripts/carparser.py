import casparser

# Get data in json format
json_str = casparser.read_cas_pdf("/Users/cponmudi/playground/investment/investment/upload-dir/22-23-2-cams.pdf","test123" , output="json")

print(json_str)
