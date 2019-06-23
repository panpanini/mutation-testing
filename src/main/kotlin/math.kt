// original
fun getItemViewType(position: Int) {
    when (items[position - HEADER_POSITION]) {
      // return item type
    }
}

// mutated
fun getItemViewType(position: Int) {
    when (items[position + HEADER_POSITION]) {
      // return item type
    }
}
