import unittest
from app import app

class PaymentServiceTestCase(unittest.TestCase):
    def setUp(self):
        self.ctx = app.test_client()

    def test_health_check(self):
        response = self.ctx.get('/health')
        self.assertEqual(response.status_code, 200)
        data = response.get_json()
        self.assertEqual(data.get('status'), 'UP')
        self.assertEqual(data.get('dependencies'), 'lightwell-remediated')

    def test_process_payment(self):
        payload = {"account": "ACCT-00123", "amount": 250.75}
        response = self.ctx.post('/api/v1/payments', json=payload)
        self.assertEqual(response.status_code, 200)
        data = response.get_json()
        self.assertEqual(data.get('status'), 'SUCCESS')
        self.assertEqual(data.get('account'), 'ACCT-00123')

if __name__ == '__main__':
    unittest.main()
