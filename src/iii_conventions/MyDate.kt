package iii_conventions

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate> {

    override operator fun compareTo(other: MyDate): Int {
        val yearcompare = year.compareTo(other.year)
        if (yearcompare != 0) {
            return yearcompare
        }
        val monthcompare = month.compareTo(other.month)
        if (monthcompare != 0) {
            return monthcompare
        }
        return dayOfMonth.compareTo(other.dayOfMonth)
    }

    operator fun plus(interval: TimeInterval): MyDate {
        return this + RepeatedTimeInterval(interval, 1)
    }

    operator fun plus(interval: RepeatedTimeInterval): MyDate {
        return this.addTimeIntervals(interval.ti, interval.n)
    }
}

operator fun MyDate.rangeTo(other: MyDate): DateRange {
    return DateRange(this, other)
}


enum class TimeInterval {
    DAY,
    WEEK,
    YEAR
}

operator fun TimeInterval.times(n: Int): RepeatedTimeInterval {
    return RepeatedTimeInterval(this, n)
}

class RepeatedTimeInterval(val ti: TimeInterval, val n: Int) {
    operator fun times(n: Int): RepeatedTimeInterval {
        return RepeatedTimeInterval(this.ti, this.n * n)
    }
}

class DateRange(override val start: MyDate, override val endInclusive: MyDate) : ClosedRange<MyDate>, Iterable<MyDate> {
    override fun iterator(): Iterator<MyDate> {
        return object : Iterator<MyDate> {
            var currentDate = start
            override fun hasNext(): Boolean {
                return currentDate <= endInclusive
            }

            override fun next(): MyDate {
                val retval = currentDate
                currentDate = currentDate.nextDay()
                return retval
            }

        }
    }

}
