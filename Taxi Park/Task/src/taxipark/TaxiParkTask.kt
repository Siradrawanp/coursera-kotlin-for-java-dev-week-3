package taxipark

/*
 * Task #1. Find all the drivers who performed no trips.
 */
fun TaxiPark.findFakeDrivers(): Set<Driver> =
        this.allDrivers.filterNot { this.trips.map { trip -> trip.driver }.contains(it) }.toSet()

/*
 * Task #2. Find all the clients who completed at least the given number of trips.
 */
fun TaxiPark.findFaithfulPassengers(minTrips: Int): Set<Passenger> =
        this.allPassengers.filter { passenger -> this.trips.flatMap { trip -> trip.passengers }
                .count { it == passenger } >= minTrips}.toSet()

/*
 * Task #3. Find all the passengers, who were taken by a given driver more than once.
 */
fun TaxiPark.findFrequentPassengers(driver: Driver): Set<Passenger> =
        this.allPassengers.filter { passenger -> this.trips.filter { trip -> trip.driver == driver }
                .flatMap { trip -> trip.passengers }.count { it == passenger } > 1}.toSet()

/*
 * Task #4. Find the passengers who had a discount for majority of their trips.
 */
fun TaxiPark.findSmartPassengers(): Set<Passenger> =
        this.allPassengers.filter { (this.trips.filter { trip -> trip.passengers.contains(it) }
                .count { ftrip -> ftrip.discount != null && ftrip.discount > 0.0 } > this.trips.filter {
                    trip -> trip.passengers.contains(it)
        }.count()/2)}.toSet()

/*
 * Task #5. Find the most frequent trip duration among minute periods 0..9, 10..19, 20..29, and so on.
 * Return any period if many are the most frequent, return `null` if there're no trips.
 */
fun TaxiPark.findTheMostFrequentTripDurationPeriod(): IntRange? {
    var tempjarak   = 10
    var durasi      = this.trips.map { trip -> trip.duration }
    val rng_durasi  : MutableMap<IntRange?,Int> = mutableMapOf(null to 0)

    while (true){
        val (parted, unparted) = durasi.partition { it < tempjarak }
        rng_durasi.put(tempjarak-10..tempjarak-1, parted.count())
        if (durasi.count() == 0 ){break}
        tempjarak += 10
        durasi = unparted
    }
    return rng_durasi.maxByOrNull { it.value }?.key
}

/*
 * Task #6.
 * Check whether 20% of the drivers contribute 80% of the income.
 */
fun TaxiPark.checkParetoPrinciple(): Boolean {
    val tot_pemasukan   = this.trips.map { trip -> trip.cost }.sum()
    val jumlah_Driver20 : Int = this.allDrivers.count()/5

    var pemasukan_driver = this.trips.map { it.driver to it.cost }.groupBy { it.first }
            .mapValues { it.value.sumByDouble { it.second } }

    if (tot_pemasukan == 0.0 || pemasukan_driver.values.sortedDescending().take(jumlah_Driver20)
                    .sum() < tot_pemasukan * 0.8){
        return false
    }
    return true
}