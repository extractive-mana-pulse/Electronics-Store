import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.e_shop.R

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun NikeStoreUI() {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            TopBar()
        }

        item {
            SearchBar()
        }

        item {
            CategoriesSection()
        }

        item {
            TopSellingSection()
        }
        item {
            TopSellingSection()
        }
        item {
            TopSellingSection()
        }
    }
}

@Composable
fun TopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Profile Image
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "Profile",
            modifier = Modifier
                .size(40.dp)
                .fillMaxSize()
        )

        // Gender Selection
        TextButton(onClick = { }) {
            Text("Men")
        }

        // Shopping Bag
        IconButton(onClick = { }) {
            Icon(
                imageVector = Icons.Default.ShoppingCart,
                contentDescription = "Shopping Bag"
            )
        }
    }
}

@Composable
fun SearchBar() {
    OutlinedTextField(
        value = "",
        onValueChange = { },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search"
            )
        },
        placeholder = { Text("Search") },
        singleLine = true
    )
}

@Composable
fun CategoriesSection() {
    Column(modifier = Modifier.padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Categories",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            TextButton(onClick = { }) {
                Text("SEE ALL")
            }
        }

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(categories) { category ->
                CategoryItem(category)
            }
        }
    }
}

@Composable
fun CategoryItem(
    category: Category
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(80.dp)
    ) {
        Card(
            modifier = Modifier.size(80.dp)
        ) {
            Image(
                painter = painterResource(id = category.imageRes),
                contentDescription = category.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = category.name,
            fontSize = 14.sp
        )
    }
}

@Composable
fun TopSellingSection() {
    Column(modifier = Modifier.padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Top Selling",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            TextButton(onClick = { }) {
                Text("See All")
            }
        }

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(topSellingItems) { item ->
                ProductCard(item)
            }
        }
    }
}

@Composable
fun ProductCard(product: Product) {
    Card(
        modifier = Modifier
            .width(180.dp)
            .padding(4.dp)
    ) {
        Column {
            Box(modifier = Modifier.height(200.dp)) {
                Image(
                    painter = painterResource(id = product.imageRes),
                    contentDescription = product.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                IconButton(
                    onClick = { },
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Favorite"
                    )
                }
            }
            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = product.name,
                    fontSize = 16.sp,
                    maxLines = 2
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "$${product.price}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                if (product.originalPrice != null) {
                    Text(
                        text = "$${product.originalPrice}",
                        fontSize = 14.sp,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.error
                        )
                    )
                }
            }
        }
    }
}

// Data Classes and Sample Data
data class Category(
    val name: String,
    val imageRes: Int
)

data class Product(
    val name: String,
    val price: Double,
    val originalPrice: Double? = null,
    val imageRes: Int
)

val categories = listOf(
    Category("Hoodies", R.drawable.ic_launcher_foreground),
    Category("Shorts", R.drawable.ic_launcher_foreground),
    Category("Shoes", R.drawable.ic_launcher_foreground),
    Category("Bag", R.drawable.ic_launcher_foreground),
    Category("Accessories", R.drawable.ic_launcher_foreground)
)

val topSellingItems = listOf(
    Product("Men's Harrington Jacket", 148.00, imageRes = R.drawable.ic_launcher_foreground),
    Product("Max Cirro Men's Slides", 55.00, 100.97, R.drawable.ic_launcher_foreground)
)


@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Preview(showBackground = true)
@Composable
fun SearchBarExample() {

    var text by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        SearchBar(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp).weight(1f).align(Alignment.CenterVertically), // Adjusted padding
            query = text,
            onQueryChange = { text = it },
            onSearch = { active = false },
            active = active,
            onActiveChange = { active = it },
            leadingIcon = {
                Icon(
                    Icons.Default.Search,
                    contentDescription = "Search icon"
                )
            },
            trailingIcon = {
                Icon(
                    Icons.Default.MoreVert,
                    contentDescription = "Microphone icon"
                )
            },
            placeholder = { Text("Search") }
        ) {
            // Search suggestions
            repeat(4) { idx ->
                val resultText = "Suggestion $idx"
                ListItem(
                    headlineContent = { Text(resultText) },
                    supportingContent = { Text("Additional info") },
                    leadingContent = { Icon(Icons.Default.Call, contentDescription = null) },
                    modifier = Modifier
                        .clickable {
                            text = resultText
                            active = false
                        }
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp)
                )
            }
        }

        GlideImage(
            model = R.drawable.ic_launcher_foreground,
            contentDescription = "Profile Image",
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SearchBarM3() {
    var query by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }

    val searchHistory = listOf("Android", "Kotlin", "Compose", "Material Design", "GPT-4")

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        SearchBar(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            query = query,
            onQueryChange = { query = it },
            onSearch = { newQuery ->
                println("Performing search on query: $newQuery")
            },
            active = active,
            onActiveChange = { active = it },
            placeholder = {
                Text(text = "Search")
            },
            leadingIcon = {
                Icon(imageVector = Icons.Filled.Search, contentDescription = "Search")
            },
            trailingIcon = {
                Row {
                    IconButton(onClick = { /**TODO: activate microphone */ }) {
                        Icon(painter = painterResource(R.drawable.mic), contentDescription = "Mic")
                    }
                    if (active) {
                        IconButton(
                            onClick = { if (query.isNotEmpty()) query = "" else active = false }
                        ) {
                            Icon(imageVector = Icons.Filled.Close, contentDescription = "Close")
                        }
                    }
                }
            }
        ) {
            searchHistory.takeLast(3).forEach { item ->
                ListItem(
                    modifier = Modifier.clickable { query = item },
                    headlineContent = { Text(text = item) },
                    leadingContent = {
                        Icon(
                            painter = painterResource(R.drawable.history),
                            contentDescription = null
                        )
                    }
                )
            }
        }
    }
}