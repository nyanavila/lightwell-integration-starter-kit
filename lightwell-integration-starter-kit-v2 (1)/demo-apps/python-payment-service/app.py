import urllib3
from flask import Flask, jsonify

app = Flask(__name__)
http = urllib3.PoolManager()

@app.route("/health")
def health():
    return jsonify({
        "status": "SECURED",
        "service": "python-payment-service",
        "urllib3_version": urllib3.__version__,
        "note": "Resolved drop-in security patch from Red Hat Lightwell without breaking API changes"
    })

if __name__ == "__main__":
    app.run(port=8080)
