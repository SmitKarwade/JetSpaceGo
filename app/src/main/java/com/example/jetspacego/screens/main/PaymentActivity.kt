package com.example.jetspacego.screens.main


import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import com.example.jetspacego.model.paymentmodel.OrderResponse
import com.example.jetspacego.viewmodel.MongoViewModel
import com.razorpay.Checkout
import org.json.JSONObject
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.jetspacego.R
import com.example.jetspacego.model.Result
import com.razorpay.PaymentData
import com.razorpay.PaymentResultWithDataListener
import dagger.hilt.android.AndroidEntryPoint
import kotlin.random.Random

@AndroidEntryPoint
class PaymentActivity : ComponentActivity(), PaymentResultWithDataListener {
    lateinit var viewModel: MongoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val navController = rememberNavController()
            viewModel = hiltViewModel()

            val missionName = intent.getStringExtra("name") ?: "No Name"
            val missionDesc = intent.getStringExtra("desc") ?: "No Description"
            val missionImageUrl = intent.getStringExtra("url") ?: "No URL"

            Log.d("PaymentActivity", "Mission Name: $missionName")
            Log.d("PaymentActivity", "Mission Description: $missionDesc")
            Log.d("PaymentActivity", "Mission Image URL: $missionImageUrl")

            Surface {
                Text(text = "PaymentActivity")
                PaymentScreen(navController, viewModel, this, name = missionName, description = missionDesc, imageUrl = missionImageUrl)
            }
        }
    }

    override fun onPaymentSuccess(razorpayPaymentID: String?, paymentData: PaymentData?) {
        viewModel.startVerification(razorpayPaymentID ?: "", paymentData?.orderId ?: "", paymentData?.signature ?: "")
        Toast.makeText(this, "Payment Successful: $razorpayPaymentID", Toast.LENGTH_LONG).show()

    }


    override fun onPaymentError(errorCode: Int, errorMessage: String?, paymentData: PaymentData?) {
        Toast.makeText(this, "Payment Failed", Toast.LENGTH_LONG).show()
    }

    fun startRazorpayCheckout(order: OrderResponse, secretId: String) {
        val checkout = Checkout()
        checkout.setKeyID(secretId)

        try {
            val options = JSONObject().apply {
                put("name", "Space Travel Booking")
                put("description", "Mission Ticket")
                put("currency", order.currency)
                put("amount", order.amount)
                put("order_id", order.orderId)
                put("prefill", JSONObject().apply {
                    put("email", "user@example.com")
                    put("contact", "9999999999")
                })
            }

            checkout.open(this, options)

        } catch (e: Exception) {
            Toast.makeText(this, "Error in payment: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
}


@Composable
fun PaymentScreen(navController: NavController, viewModel: MongoViewModel, activity: PaymentActivity, name: String?, description: String?, imageUrl: String?) {
    Surface {
        if (name != null && description != null && imageUrl != null) {
            InitiatePayment(
                missionName = name,
                missionDescription = description,
                missionImageUrl = imageUrl,
                onPaymentProceed = { amt, secretId ->
                    StartPaymentProcess(amt, viewModel, secretId, activity)
                }
            )
        }
    }
}

fun StartPaymentProcess(amt: Double, viewModel: MongoViewModel, secretId: String, activity: PaymentActivity) {
    viewModel.startPayment(amt)
    viewModel.stateOrder.value.let {
        if (it.txnId != null && it.orderId != null && it.amount != null && it.currency != null) {
            activity.startRazorpayCheckout(it, secretId)
        }
    }
}



@Composable
fun InitiatePayment(
    missionName: String,
    missionDescription: String,
    missionImageUrl: String?,
    onPaymentProceed: (Double, String) -> Unit
) {
    val payableAmount = remember { Random.nextDouble(5000.0, 20000.0) }
    val secretId = stringResource(id = R.string.secret_id)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Mission Preview",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )


        missionImageUrl?.let {
            GlideImage(
                model = missionImageUrl,
                contentDescription = "Mission Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(200.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.Gray)
            )
        }

        Text(
            text = missionName,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = missionDescription,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        Text(
            text = "Total Payable Amount: ₹${String.format("%.2f", payableAmount)}",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )

        Button(
            onClick = { onPaymentProceed(payableAmount, secretId) },
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            Text(text = "Proceed to Payment", fontSize = 18.sp)
        }
    }
}