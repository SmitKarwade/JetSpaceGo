package com.example.jetspacego.screens.listbook


import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.jetspacego.model.launches.BookingDetails
import com.example.jetspacego.model.launches.Results
import com.example.jetspacego.screens.main.PaymentActivity
import com.example.jetspacego.viewmodel.MongoViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@Composable
fun ListBook(navController: NavController, viewModel: MongoViewModel = hiltViewModel()) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var nationality by remember { mutableStateOf("") }
    var experience by remember { mutableStateOf("") }
    var emergencyContact by remember { mutableStateOf("") }
    var suitSize by remember { mutableStateOf("") }
    var insurance by remember { mutableStateOf(false) }
    var mealType by remember { mutableStateOf("") }

    val mission = navController.previousBackStackEntry?.savedStateHandle?.get<Results>("added msn")

    ListBookContent(
        firstName = firstName,
        onFirstNameChange = { firstName = it },
        lastName = lastName,
        onLastNameChange = { lastName = it },
        email = email,
        onEmailChange = { email = it },
        phone = phone,
        onPhoneChange = { phone = it },
        address = address,
        onAddressChange = { address = it },
        nationality = nationality,
        onNationalityChange = { nationality = it },
        experience = experience,
        onExperienceChange = { experience = it },
        emergencyContact = emergencyContact,
        onEmergencyContactChange = { emergencyContact = it },
        suitSize = suitSize,
        onSuitSizeChange = { suitSize = it },
        mealType = mealType,
        onMealTypeChange = { mealType = it },
        insurance = insurance,
        onInsuranceChange = { insurance = it },
        onProceedToPayment = {
            val validationResult = validateForm(
                firstName,
                lastName,
                email,
                phone,
                nationality,
                experience,
                emergencyContact,
                suitSize,
                mealType,
                navController.context
            )
            if (validationResult) {
                val bookingDetails = mission?.name?.let {
                    BookingDetails(
                        firstName = firstName,
                        lastName = lastName,
                        dob = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                        email = email,
                        phone = phone,
                        address = address,
                        nationality = nationality,
                        experience = experience,
                        emergencyContact = emergencyContact,
                        suitSize = suitSize,
                        insurance = insurance,
                        mealType = mealType,
                        msnId = mission.name!!
                    )
                }
                if (bookingDetails != null) {
                    viewModel.addDetails(bookingDetails)
                }
                val context = navController.context
                val intent = Intent(context, PaymentActivity::class.java).apply {
                    putExtra("name", mission?.name)
                    putExtra("desc", mission?.mission?.description)
                    putExtra("url", mission?.image?.imageUrl)
                }
                context.startActivity(intent)
            }
        }

    )
}

@Composable
fun ListBookContent(
    firstName: String,
    onFirstNameChange: (String) -> Unit,
    lastName: String,
    onLastNameChange: (String) -> Unit,
    email: String,
    onEmailChange: (String) -> Unit,
    phone: String,
    onPhoneChange: (String) -> Unit,
    address: String,
    onAddressChange: (String) -> Unit,
    nationality: String,
    onNationalityChange: (String) -> Unit,
    experience: String,
    onExperienceChange: (String) -> Unit,
    emergencyContact: String,
    onEmergencyContactChange: (String) -> Unit,
    suitSize: String,
    onSuitSizeChange: (String) -> Unit,
    mealType: String,
    onMealTypeChange: (String) -> Unit,
    insurance: Boolean,
    onInsuranceChange: (Boolean) -> Unit,
    onProceedToPayment: () -> Unit
) {
    val scrollstate = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollstate),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(text = "Personal Information", style = MaterialTheme.typography.titleLarge)

        OutlinedTextField(value = firstName, onValueChange = onFirstNameChange, label = { Text("First Name") })
        OutlinedTextField(value = lastName, onValueChange = onLastNameChange, label = { Text("Last Name") })


        OutlinedTextField(value = email, onValueChange = onEmailChange, label = { Text("Email") })
        OutlinedTextField(value = phone, onValueChange = onPhoneChange, label = { Text("Phone Number") })
        OutlinedTextField(value = address, onValueChange = onAddressChange, label = { Text("Address") })

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Additional Details", style = MaterialTheme.typography.titleLarge)

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = nationality,
            onValueChange = onNationalityChange,
            label = { Text("Nationality") }
        )

        DropdownField(
            label = "Spaceflight Experience",
            options = listOf("First Time", "Trained", "Space Enthusiast"),
            selectedOption = experience,
            onOptionSelected = onExperienceChange
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = emergencyContact,
            onValueChange = onEmergencyContactChange,
            label = { Text("Emergency Contact") }
        )

        DropdownField(
            label = "Space Suit Size",
            options = listOf("S", "M", "L", "XL"),
            selectedOption = suitSize,
            onOptionSelected = onSuitSizeChange
        )

        DropdownField(
            label = "Preferred Meal Type",
            options = listOf("Vegan", "Regular", "Space Ration"),
            selectedOption = mealType,
            onOptionSelected = onMealTypeChange
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            Switch(
                checked = insurance,
                onCheckedChange = onInsuranceChange
            )
            Text(text = "Purchased Space Insurance", modifier = Modifier.padding(start = 8.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            OutlinedButton(
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .wrapContentWidth(),
                shape = RoundedCornerShape(10.dp),
                onClick = onProceedToPayment
            ) {
                Text("Proceed to Payment", style = TextStyle(fontSize = 16.sp))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownField(
    label: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it }
    ) {
        OutlinedTextField(
            value = selectedOption,
            onValueChange = {},
            label = { Text(label) },
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}

fun validateForm(
    firstName: String, lastName: String, email: String, phone: String,
    nationality: String, experience: String, emergencyContact: String,
    suitSize: String, mealType: String, context: Context
): Boolean {
    var data = true
    if (firstName.isBlank() || lastName.isBlank()) {
        Toast.makeText(context, "Please enter your name.", Toast.LENGTH_SHORT).show()
        data = false
    }
    if (!email.contains("@")) {
        Toast.makeText(context, "Invalid email format.", Toast.LENGTH_SHORT).show()
        data = false
    }
    if (phone.length < 10) {
        Toast.makeText(context, "Invalid phone number.", Toast.LENGTH_SHORT).show()
        data = false
    }
    if (nationality.isBlank()) {
        Toast.makeText(context, "Please select a nationality.", Toast.LENGTH_SHORT).show()
        data = false
    }
    if (experience.isBlank()) {
        Toast.makeText(context, "Please select a spaceflight experience.", Toast.LENGTH_SHORT).show()
        data = false
    }
    if (emergencyContact.length < 10) {
        Toast.makeText(context, "Invalid emergency contact number.", Toast.LENGTH_SHORT).show()
        data = false
    }
    if (suitSize.isBlank()) {
        Toast.makeText(context, "Please select a suit size.", Toast.LENGTH_SHORT).show()
        data = false
    }
    if (mealType.isBlank()) {
        Toast.makeText(context, "Please select a meal type.", Toast.LENGTH_SHORT).show()
        data = false
    }
    return data
}