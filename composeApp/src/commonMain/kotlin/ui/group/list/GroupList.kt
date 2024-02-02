package ui.group.list

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import data.Group

@Composable
fun GroupList(groups: List<Group>) {
    LazyColumn(modifier = Modifier.padding(10.dp)) {
        items(items = groups) { group ->
            GroupCard(group = group)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupCard(group: Group) {
    OutlinedCard(onClick = {}) {
        Text(text = group.name, modifier = Modifier.fillMaxWidth().padding(all = 10.dp))
    }
}
