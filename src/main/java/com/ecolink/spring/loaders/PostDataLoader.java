package com.ecolink.spring.loaders;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ecolink.spring.entity.Comment;
import com.ecolink.spring.entity.Ods;
import com.ecolink.spring.entity.Post;
import com.ecolink.spring.entity.Startup;
import com.ecolink.spring.service.CommentService;
import com.ecolink.spring.service.OdsService;
import com.ecolink.spring.service.PostService;
import com.ecolink.spring.service.StartupService;

@Component
@Order(5)
public class PostDataLoader implements CommandLineRunner {

        @Autowired
        private PostService service;

        @Autowired
        private StartupService startupService;

        @Autowired
        private OdsService odsService;

        @Autowired
        private CommentService commentService;

        @Transactional
        @Override
        public void run(String... args) throws Exception {
                Startup flavorScale = startupService.findByName("FlavorScale");
                Startup climaider = startupService.findByName("Climaider");
                Startup seasony = startupService.findByName("Seasony");
                Startup tribe = startupService.findByName("Tribe");
                Startup peltarion = startupService.findByName("Peltarion");
                Startup donkeyRepublic = startupService.findByName("Donkey Republic");
                Startup pickYourPour = startupService.findByName("Pick Your Pour AS");
                Startup aliceAi = startupService.findByName("Alice AI");
                Startup leiaHealth = startupService.findByName("LEIA Health");
                Startup rightHub = startupService.findByName("RightHub");
                Startup memmora = startupService.findByName("Memmora");
                Startup soilSense = startupService.findByName("SoilSense");
                Startup monta = startupService.findByName("Monta");
                Startup doublepoint = startupService.findByName("Doublepoint");
                Startup strise = startupService.findByName("Strise");
                Startup station = startupService.findByName("Station");
                Startup silvi = startupService.findByName("Silvi");
                Startup nutrishAi = startupService.findByName("Nutrish.ai");
                Startup kodiakHub = startupService.findByName("KodiakHub");
                Startup ecoTree = startupService.findByName("EcoTree");
                Startup goMore = startupService.findByName("GoMore");
                Startup goodWings = startupService.findByName("GoodWings");
                Startup glintSolar = startupService.findByName("GlintSolar");
                Startup spritju = startupService.findByName("Spritju");
                Startup deepBlu = startupService.findByName("DeepBlu");
                Startup vove = startupService.findByName("Vove");
                Startup perPlant = startupService.findByName("PerPlant");
                Startup dripdrop = startupService.findByName("Dripdrop");
                Startup legitify = startupService.findByName("Legitify");
                Startup whistleSystem = startupService.findByName("WhistleSystem");
                Startup beCause = startupService.findByName("BeCause");
                Startup chronosHub = startupService.findByName("ChronosHub");

                // Sustainable Development Goals (SDGs)
                Ods zeroHunger = odsService.findByName("Zero Hunger");
                Ods goodHealthWellBeing = odsService.findByName("Good Health and Well-being");
                Ods qualityEducation = odsService.findByName("Quality Education");
                Ods cleanWaterSanitation = odsService.findByName("Clean Water and Sanitation");
                Ods affordableCleanEnergy = odsService.findByName("Affordable and Clean Energy");
                Ods industryInnovation = odsService.findByName("Industry, Innovation, and Infrastructure");
                Ods reducedInequalities = odsService.findByName("Reduced Inequalities");
                Ods sustainableCities = odsService.findByName("Sustainable Cities and Communities");
                Ods responsibleConsumption = odsService.findByName("Responsible Consumption and Production");
                Ods climateAction = odsService.findByName("Climate Action");
                Ods lifeBelowWater = odsService.findByName("Life Below Water");
                Ods lifeOnLand = odsService.findByName("Life on Land");
                Ods partnershipsForTheGoals = odsService.findByName("Partnerships for the Goals");
                Ods genderEquality = odsService.findByName("Gender Equality");
                Ods decentWorkAndEconomicGrowth = odsService.findByName("Decent Work and Economic Growth");
                Ods peaceJusticeAndStrongInstitutions = odsService
                                .findByName("Peace, Justice, and Strong Institutions");

                List<Post> posts = Arrays.asList(
                                new Post(
                                                flavorScale,
                                                "Revolutionizing Food Discovery",
                                                "Connecting food enthusiasts with restaurants.",
                                                "<h2>FlavorScale</h2><p><strong>Revolutionizing Food Discovery</strong></p><p>FlavorScale aspires to redefine how we experience dining by connecting food lovers with an ever-growing range of eateries, from cozy local favorites to the latest culinary hotspots. Through curated recommendations based on user preferences, it removes the guesswork of where to go or what to try next, encouraging adventurous eating without the fear of disappointment. By integrating social features like reviews, shared wish lists, and photo uploads, FlavorScale cultivates a virtual community bound by the joy of exploring diverse flavors and cuisines. Whether you’re an experienced gourmand or just dipping your toes into the gastronomic world, the platform’s seamless design makes discovering new dishes straightforward and delightful. In essence, FlavorScale elevates the act of finding a meal into a communal, story-driven adventure, inspiring everyone to celebrate the art and pleasure of eating.</p>",
                                                LocalDate.now()),
                                new Post(
                                                climaider,
                                                "Empowering SMEs for Climate Action",
                                                "Helping businesses measure and reduce their carbon footprint.",
                                                "<h2>Climaider</h2><p><strong>Empowering SMEs for Climate Action</strong></p><p>Climaider takes the complexity out of going green by equipping small and medium-sized enterprises with accessible carbon accounting and reduction strategies. Rather than drowning teams in complicated spreadsheets or abstract targets, it provides intuitive dashboards and clear action plans that highlight both immediate wins and long-term goals. By showcasing tangible progress—like emission declines or energy savings—Climaider helps businesses build trust with stakeholders, from environmentally conscious consumers to potential investors. Moreover, the platform offers educational resources and continuous guidance, making sure even those new to sustainability can confidently navigate carbon offsets, renewable energy options, and regulatory requirements. Ultimately, Climaider proves that climate leadership isn’t the exclusive domain of large corporations; smaller ventures can also leave a powerful impact on the path to a healthier planet.</p>",
                                                LocalDate.now().minusDays(5)),
                                new Post(
                                                seasony,
                                                "Making Vertical Farming Profitable",
                                                "Smart automation solutions for vertical farms.",
                                                "<h2>Seasony</h2><p><strong>Making Vertical Farming Profitable</strong></p><p>Seasony champions a future where high-yield, resource-efficient vertical farms flourish in spaces once overlooked—like city rooftops, abandoned warehouses, and underused industrial sites. By offering automation that handles everything from seed planting to crop monitoring, it empowers growers to slash labor costs and maintain pristine growing conditions. Detailed analytics further support this process, revealing trends in crop performance and pinpointing the ideal balance of light, humidity, and nutrients. This fine-tuned approach not only boosts output but also mitigates environmental impacts, helping urban areas evolve into self-sustaining food sources. Seasony’s ultimate mission is to transform vertical farming into a mainstream, thriving industry, reshaping how entire populations think about fresh produce and sustainability.</p>",
                                                LocalDate.now().minusDays(10)),
                                new Post(
                                                tribe,
                                                "Simplifying Insurance",
                                                "Peer-to-peer insurance for a fairer world.",
                                                "<h2>Tribe</h2><p><strong>Simplifying Insurance</strong></p><p>Tribe is on a mission to replace the opaque and often expensive world of traditional insurance with something far more human and communal. Rather than funneling money into inscrutable corporate structures, members pool resources in a transparent, collaborative manner where everyone understands exactly how funds move. This model is not just about lower premiums or quicker payouts; it’s about forging genuine connections and distributing risk in a way that feels equitable to all. The system promotes open dialogue, allowing participants to engage in decision-making and build a culture of accountability. At its heart, Tribe proves that with the right technology and mindset, insurance can be about empathy, fairness, and shared responsibility—values often forgotten in conventional finance.</p>",
                                                LocalDate.now().minusWeeks(1)),
                                new Post(
                                                peltarion,
                                                "AI for a Better Future",
                                                "Leveraging AI for health, wealth, and sustainability.",
                                                "<h2>Peltarion</h2><p><strong>AI for a Better Future</strong></p><p>Peltarion lowers the barrier to machine learning and artificial intelligence, enabling organizations of all sizes to harness complex algorithms for real-world solutions. Picture a hospital using Peltarion to identify early signs of disease, a financial firm improving risk management, or a nonprofit pinpointing areas most in need of environmental interventions. By simplifying AI model creation, iteration, and deployment, it untangles the knots of coding expertise and technical jargon, allowing domain experts to focus on innovation rather than the intricacies of data engineering. Built on values of openness and collaboration, Peltarion also encourages knowledge-sharing across disciplines, broadening AI’s impact beyond tech giants. In doing so, it champions a future where AI’s power is distributed more evenly across society and directed toward pressing human challenges.</p>",
                                                LocalDate.now().minusMonths(1)),
                                new Post(
                                                donkeyRepublic,
                                                "Leading Bike Sharing",
                                                "Promoting sustainable urban mobility.",
                                                "<h2>Donkey Republic</h2><p><strong>Leading Bike Sharing</strong></p><p>Donkey Republic propels cities toward a healthier, more eco-friendly future by placing a convenient fleet of bicycles right at people’s fingertips. Unlike traditional bike-sharing systems confined to select docking stations, it embraces a flexible pickup-and-drop-off model that fits seamlessly into urban life. Riders can unlock bikes with a quick tap on their phones, boosting accessibility and ensuring minimal delays. By filling the streets with easily accessible two-wheelers, Donkey Republic reduces carbon emissions, eases traffic jams, and fosters closer connections between locals and their neighborhoods. The result is an inclusive, people-centered cityscape, where everyday journeys become opportunities to breathe fresh air and appreciate one’s surroundings rather than stress over parking or gridlock.</p>",
                                                LocalDate.now()),
                                new Post(
                                                pickYourPour,
                                                "Personalized Tasting Experiences",
                                                "Digital menus for food and drink pairings.",
                                                "<h2>Pick Your Pour</h2><p><strong>Personalized Tasting Experiences</strong></p><p>Pick Your Pour blends gastronomic creativity with digital convenience, offering tailored food-and-beverage pairings that delight the taste buds and feed culinary curiosity. By analyzing user preferences—from flavor intensity to dietary restrictions—it curates suggestions that might span wine, craft beer, artisanal cocktails, or even non-alcoholic mocktails. Restaurants adopting this platform can impress guests with a truly interactive menu, while adventurous diners can step out of their comfort zones in a fun, guided manner. Beyond the meal itself, Pick Your Pour encourages storytelling: every recommendation comes with a backstory about ingredients, culture, or origin. As a result, each bite or sip transforms into an immersive dining journey that extends well beyond the plate.</p>",
                                                LocalDate.now().minusDays(3)),
                                new Post(
                                                aliceAi,
                                                "Democratizing Learning",
                                                "Personalized learning experiences for students.",
                                                "<h2>Alice AI</h2><p><strong>Democratizing Learning</strong></p><p>Alice AI breaks through the rigidity of one-size-fits-all schooling by crafting bespoke learning paths that elevate students based on their unique progress and potential. By integrating multimedia content—ranging from interactive quizzes to gamified challenges—it captures attention in a way traditional textbooks often cannot. The platform’s sophisticated analytics offer teachers detailed insights into class performance, allowing them to address gaps and celebrate achievements promptly. Meanwhile, learners benefit from immediate feedback loops, reinforcing core concepts before misconceptions can take root. Through this synergy of technology and pedagogy, Alice AI aspires to foster a generation of self-driven, empowered thinkers who genuinely enjoy the process of learning.</p>",
                                                LocalDate.now().minusDays(8)),
                                new Post(
                                                leiaHealth,
                                                "Supporting Parents with AI",
                                                "Digitizing the parental journey.",
                                                "<h2>LEIA Health</h2><p><strong>Supporting Parents with AI</strong></p><p>LEIA Health reimagines the entire arc of parenthood by consolidating medical advice, child development milestones, and emotional support into one smart, AI-powered companion. Whether tackling sleepless nights, teething troubles, or the excitement of a toddler’s first steps, caregivers can quickly access personalized suggestions grounded in professional expertise. This goes hand in hand with interactive forums where parents can swap tips, share stories, and gain solidarity from peers walking a similar path. By coupling advanced data analysis with compassion, LEIA Health not only eases the stress of day-to-day parenting but also nurtures a sense of community. Ultimately, it highlights that being informed and connected can make the pivotal years of raising a child even more rewarding.</p>",
                                                LocalDate.now().minusWeeks(2)),
                                new Post(
                                                rightHub,
                                                "Simplifying IP Management",
                                                "All-in-one platform for intellectual property.",
                                                "<h2>RightHub</h2><p><strong>Simplifying IP Management</strong></p><p>RightHub addresses the intricacies of intellectual property head-on, ensuring innovators can maintain focus on creation rather than legal paperwork. By organizing trademarks, patents, and other protections under a single digital umbrella, it takes the guesswork out of deadlines, renewals, and compliance. This unified approach is further enhanced by built-in alerts and updates, allowing users to act proactively and prevent lapses in valuable rights. More than just a management tool, RightHub’s secure database fosters collaboration among legal teams, R&D departments, and individual creators. In a world where originality and invention fuel growth, RightHub ensures groundbreaking ideas remain guarded and free to flourish.</p>",
                                                LocalDate.now().minusDays(12)),
                                new Post(
                                                memmora,
                                                "Caring for Graves with Technology",
                                                "Combining technology with gardening.",
                                                "<h2>Memmora</h2><p><strong>Caring for Graves with Technology</strong></p><p>Memmora offers a thoughtful, tech-savvy approach to honoring deceased loved ones, reflecting respect for the past while embracing the present’s digital possibilities. At its core, it provides scheduling and upkeep services for grave sites, sparing families and friends the challenges of distance or lack of time. In addition, the platform supports personalized tributes, such as memorial notes, photos, and tokens of remembrance. These heartfelt gestures link individuals across regions and generations, creating an inclusive circle of remembrance. By blending horticultural care with online connectivity, Memmora shifts the focus toward celebrating lives lived, ensuring that memories remain vibrant even when physical visits aren’t always possible.</p>",
                                                LocalDate.now().minusDays(6)),
                                new Post(
                                                soilSense,
                                                "Tackling Water Scarcity",
                                                "Agri-tech solutions for sustainable farming.",
                                                "<h2>SoilSense</h2><p><strong>Tackling Water Scarcity</strong></p><p>SoilSense enters the agricultural arena with a mission to conserve one of the planet’s most precious commodities: water. Its precise sensor network tracks moisture levels and nutrient flows, letting farmers calibrate irrigation schedules right down to the minute. In turn, each crop receives exactly what it needs, minimizing runoff and maximizing growth potential. Over time, these targeted interventions contribute to healthier soil that retains moisture more effectively, further supporting long-term farm viability. Instead of resorting to guesswork, SoilSense users make data-driven choices that not only increase yields but also steward finite resources. As global populations rise, this blend of high-tech solutions and ecological mindfulness offers a hopeful blueprint for feeding the world.</p>",
                                                LocalDate.now().minusDays(15)),
                                new Post(
                                                monta,
                                                "Powering EV Charging",
                                                "Integrated software for electric vehicle charging.",
                                                "<h2>Monta</h2><p><strong>Powering EV Charging</strong></p><p>Monta orchestrates the symphony of electric vehicle charging by harmonizing hardware, user apps, and financial processes into one cohesive ecosystem. Charging station owners can effortlessly monitor power usage, set rates, and track revenue, while drivers receive an intuitive interface that locates nearby stations and manages payments. The outcome is a frictionless charging experience that balances grid loads and promotes widespread EV adoption. Beyond functionality, Monta dives into analytics, identifying trends and peak usage times that influence smarter energy distribution. In fostering a transparent, user-friendly environment, Monta helps redefine modern mobility and speeds the shift away from fossil-fuel dependence.</p>",
                                                LocalDate.now().minusDays(7)),
                                new Post(
                                                doublepoint,
                                                "Gesture Recognition Technology",
                                                "Innovative software for smartwatches.",
                                                "<h2>Doublepoint</h2><p><strong>Gesture Recognition Technology</strong></p><p>Doublepoint catapults wearable computing into a new era by interpreting hand gestures with remarkable precision and minimal delay. Imagine controlling music playlists, fitness apps, or even receiving calls through simple swipes and taps in the air, all without fumbling for tiny smartwatch screens. This natural interaction style promises to reduce distractions and enhance safety, especially when users are on the move. The technology also has enormous potential in gaming, virtual reality, and assistive devices, opening countless pathways for seamless, intuitive control. By marrying sophisticated sensor data with sleek design, Doublepoint presents a world where the boundary between human motion and digital commands nearly disappears.</p>",
                                                LocalDate.now().minusDays(20)),
                                new Post(
                                                strise,
                                                "Fighting Financial Crime",
                                                "AML automation for financial institutions.",
                                                "<h2>Strise</h2><p><strong>Fighting Financial Crime</strong></p><p>Strise provides a lifeline to banks and financial organizations facing ever-evolving threats like money laundering, fraud, and illicit transfers. By constantly scanning massive data sets and applying advanced analytics, it rapidly flags suspicious patterns and transactions before they escalate. The platform’s streamlined workflow then guides compliance teams in documenting and resolving issues swiftly, meeting stringent regulatory obligations. This approach not only minimizes the likelihood of severe penalties but also safeguards clients and businesses from reputational damage. As a result, Strise injects confidence into the financial ecosystem, reminding the world that with the right tools, transparency and accountability can prevail.</p>",
                                                LocalDate.now().minusWeeks(3)),
                                new Post(
                                                station,
                                                "Empowering Students",
                                                "Creating inclusive student communities.",
                                                "<h2>Station</h2><p><strong>Empowering Students</strong></p><p>Station Fonden envisions a new kind of collegiate experience built on inclusion, support, and opportunity. Its programs range from peer mentorship initiatives, where upperclassmen guide newcomers, to resource hubs that help students tackle real-life challenges like budgeting or internship hunting. By removing barriers—be they social, financial, or academic—Station fosters a sense of unity across diverse campus populations. This culture of belonging endures long after graduation, as alumni remain connected through ongoing events, panels, and networking. By focusing on holistic student well-being, Station Fonden reshapes higher education into a space where every individual can flourish on their own terms.</p>",
                                                LocalDate.now().minusDays(18)),
                                new Post(
                                                silvi,
                                                "AI for Research",
                                                "Tools for systematic literature reviews.",
                                                "<h2>Silvi</h2><p><strong>AI for Research</strong></p><p>Silvi accelerates scholarship by managing one of the most time-consuming phases of research: the literature review. Its sophisticated algorithms crawl through academic databases, curating a refined set of articles relevant to each specific query. Researchers can then rank, filter, or annotate sources, moving directly into deeper analysis without the typical drudgery of manual sifting. This streamlined process democratizes research, particularly for interdisciplinary teams who must navigate multiple fields and terminologies. By shaving off hours spent on preliminary searches, Silvi frees intellects to focus on synthesis, debate, and breakthrough discoveries. In short, it’s the ideal ally for thinkers ready to push the boundaries of their fields.</p>",
                                                LocalDate.now().minusWeeks(4)),
                                new Post(
                                                nutrishAi,
                                                "Personalized Nutrition",
                                                "AI-driven diet recommendations.",
                                                "<h2>Nutrish.ai</h2><p><strong>Personalized Nutrition</strong></p><p>Nutrish.ai reshapes how individuals approach health and meal planning by blending data science with a personal touch. From analyzing dietary preferences and cooking habits to factoring in exercise levels and medical constraints, it formulates meal plans that align with both taste and wellness goals. Daily or weekly updates remind users to stay on track and adjust portions as needed, ensuring progress without the stigma of rigid diet rules. The platform also integrates motivational content, such as recipe inspiration and progress badges, transforming nutrition into a positive, growth-oriented experience. Ultimately, Nutrish.ai fosters a deeper understanding of how food fuels our lives, providing tools to build a healthier relationship with mealtime choices.</p>",
                                                LocalDate.now().minusDays(9)),
                                new Post(
                                                kodiakHub,
                                                "Sustainable Supplier Relationships",
                                                "SRM platform for procurement teams.",
                                                "<h2>Kodiak Hub</h2><p><strong>Sustainable Supplier Relationships</strong></p><p>Kodiak Hub unifies procurement and sustainability by giving teams a bird’s-eye view of supplier practices, compliance levels, and potential ethical red flags. The platform’s robust evaluation metrics help companies measure everything from carbon footprints and labor standards to cost structures and timely deliveries. This heightened transparency allows businesses to prioritize vendors that align with their core values—be it fair wages, minimal waste, or responsible sourcing of materials. Moreover, Kodiak Hub’s social tools facilitate open dialogue, encouraging both parties to continuously refine their environmental and ethical commitments. By bridging mutual interests and high-quality standards, it drives an era where social responsibility and profitability move in tandem.</p>",
                                                LocalDate.now().minusDays(14)),
                                new Post(
                                                ecoTree,
                                                "Owning Trees for Sustainability",
                                                "Making sustainability accessible.",
                                                "<h2>EcoTree</h2><p><strong>Owning Trees for Sustainability</strong></p><p>EcoTree introduces a tangible, rewarding way to invest in the planet by allowing individuals and organizations to purchase actual trees in well-managed forests. Over time, these trees mature, absorbing carbon dioxide and contributing to biodiversity, while any sustainable timber harvesting yields potential returns for the owners. By connecting each participant’s personal stake to real environmental outcomes, EcoTree transforms green goals from distant ideals into day-to-day realities. The model also emphasizes transparent stewardship, making it easy to see exactly where and how each tree thrives. As a result, contributors become not just investors but genuine stakeholders in restoring and preserving the ecosystems that sustain life on Earth.</p>",
                                                LocalDate.now().minusDays(11)),
                                new Post(
                                                goMore,
                                                "Sharing Cars for a Greener Future",
                                                "Platform for ridesharing and car rentals.",
                                                "<h2>GoMore</h2><p><strong>Sharing Cars for a Greener Future</strong></p><p>GoMore pushes forward the concept of shared mobility by turning every car into a potential transport solution. Its network pairs travelers seeking rides with drivers who have free seats, as well as offering a straightforward rental system for short trips. This model significantly reduces the number of underused vehicles on roads, alleviating congestion and cutting emissions at the same time. In essence, it gives people a compelling reason to see cars as communal resources rather than personal property gathering dust. By promoting cost-effective and eco-friendly travel, GoMore not only improves the urban landscape but also fosters a spirit of collaboration among neighbors, friends, and even strangers heading in the same direction.</p>",
                                                LocalDate.now().minusWeeks(5)),
                                new Post(
                                                goodWings,
                                                "Climate-Friendly Travel",
                                                "Reducing emissions through sustainable travel.",
                                                "<h2>GoodWings</h2><p><strong>Climate-Friendly Travel</strong></p><p>GoodWings ventures beyond typical hotel booking platforms by weaving sustainability into the heart of every journey. It provides users with a transparent overview of their trip’s carbon footprint, along with curated offsetting solutions and eco-conscious accommodations. By making it easy to see exactly how each booking affects the environment, GoodWings sparks a mindset shift: travelers become active partners in addressing climate change, not just passive consumers of transport and lodging. The platform also partners with mission-aligned charities and NGOs, further amplifying its impact. Through convenience, data, and collaboration, GoodWings proves that exploring the world can go hand-in-hand with protecting it for generations to come.</p>",
                                                LocalDate.now().minusDays(22)),
                                new Post(
                                                glintSolar,
                                                "Accelerating Solar Energy",
                                                "Screening and analysis software for solar projects.",
                                                "<h2>Glint Solar</h2><p><strong>Accelerating Solar Energy</strong></p><p>Glint Solar supercharges the adoption of renewable energy by evaluating potential solar sites with unmatched accuracy and speed. It aggregates data on weather patterns, elevation, shading obstacles, and more, simplifying what was once a complex web of planning stages. This comprehensive approach quickly identifies areas with optimal sunlight hours, ensuring less wasted land and quicker returns on investment. By speeding up due diligence, Glint Solar streamlines the entire project lifecycle, from early feasibility checks to final installations. Its user-friendly interface also bridges gaps between technical experts and financiers, uniting all stakeholders in a shared vision of clean, abundant power derived straight from the sun’s rays.</p>",
                                                LocalDate.now().minusDays(16)),
                                new Post(
                                                spritju,
                                                "Revolutionizing Energy Tracking",
                                                "Marketplace for energy certificates.",
                                                "<h2>Spritju</h2><p><strong>Revolutionizing Energy Tracking</strong></p><p>Spritju pioneers an agile marketplace where companies can directly trade renewable energy certificates, tracking transactions in real time for utmost transparency. This innovation empowers businesses to demonstrate their commitment to clean power sources with certainty, sidestepping the complexities often associated with intangible certificates and outdated systems. Buyers gain a clear view of who generated the energy and how, while sellers can swiftly reinvest proceeds in expanding sustainable operations. By allowing consumers and producers to interact so openly, Spritju boosts market efficiency and overall trust in green energy. The result is an integrated, data-rich environment that inspires further expansion of wind, solar, and other renewables.</p>",
                                                LocalDate.now().minusDays(19)),
                                new Post(
                                                deepBlu,
                                                "Connecting Divers",
                                                "Community for sharing dive logs.",
                                                "<h2>DeepBlu</h2><p><strong>Connecting Divers</strong></p><p>Deepblu is more than just a dive log; it’s a thriving social hub for everyone who’s ever felt the allure of the deep sea. Users can catalog their underwater voyages with vivid photos, detailed route maps, and personal anecdotes—effectively building a living scrapbook of aquatic exploration. The platform also helps prospective divers discover prime locations, gear suggestions, and safety tips, elevating skill levels within the community. Beyond technical insights, Deepblu fosters environmental stewardship, highlighting marine conservation projects and encouraging volunteer efforts. By blending adventure, shared passion, and a sense of responsibility for ocean life, Deepblu transforms diving into a collective global endeavor.</p>",
                                                LocalDate.now().minusWeeks(6)),
                                new Post(
                                                vove,
                                                "Sustainable Household Essentials",
                                                "Eco-friendly products for everyday use.",
                                                "<h2>Vove</h2><p><strong>Sustainable Household Essentials</strong></p><p>Vove aims to simplify conscious living by curating an extensive array of sustainable household goods designed for everyday convenience. From biodegradable cleaning supplies to zero-waste personal care products, each item undergoes rigorous vetting for eco-friendly production, ethical sourcing, and minimal packaging. This commitment ensures consumers can shop confidently, eliminating the guesswork in choosing greener alternatives. As environmental awareness grows, Vove helps families replace habitual single-use plastics and harmful chemicals with planet-friendly substitutes, step by step. Ultimately, it reshapes routines, proving that small, consistent choices in daily life can ignite significant, long-lasting environmental benefits.</p>",
                                                LocalDate.now().minusDays(21)),
                                new Post(
                                                perPlant,
                                                "AI for Sustainable Farming",
                                                "Sensor technology for precision agriculture.",
                                                "<h2>PerPlant</h2><p><strong>AI for Sustainable Farming</strong></p><p>PerPlant marries modern technology and time-tested farming practices to drive smart, data-centric agriculture. By embedding fields with AI-compatible sensors, it delivers real-time metrics on soil moisture, crop height, pest infestations, and nutrient deficiencies. This on-the-spot information empowers farmers to finetune irrigation, pesticide usage, and fertilization with surgical precision, cutting costs and preserving ecosystems. Over time, PerPlant’s predictive analytics also foresee weather fluctuations and market demands, helping growers plan harvests that minimize waste and meet consumer needs. In bridging science, sustainability, and profit, PerPlant serves as a guiding light for the next generation of productive, responsible farms.</p>",
                                                LocalDate.now().minusDays(13)),
                                new Post(
                                                dripdrop,
                                                "Eco-Friendly Umbrella Rentals",
                                                "Sustainable solutions for hotels.",
                                                "<h2>Dripdrop</h2><p><strong>Eco-Friendly Umbrella Rentals</strong></p><p>Dripdrop rethinks the humble umbrella by offering a rental solution that leaves behind the single-use, disposable culture plaguing so many guest amenities. Hotels can stock Dripdrop’s reclaimed or recycled umbrellas and lend them to guests at check-in, ensuring everyone stays dry without generating unnecessary trash. This creative twist on a common item not only saves money for hospitality businesses but also sets them apart as eco-conscious establishments. Travelers too can feel good about doing their part, returning umbrellas after use with minimal fuss. In a world where convenience too often comes at the planet’s expense, Dripdrop steps in to blend practicality, style, and sustainability into a single, forward-thinking service.</p>",
                                                LocalDate.now().minusDays(17)),
                                new Post(
                                                legitify,
                                                "Remote Online Notarization",
                                                "AI-powered platform for legal documents.",
                                                "<h2>Legitify</h2><p><strong>Remote Online Notarization</strong></p><p>Legitify breathes fresh life into the legal process by letting users authenticate documents from anywhere with a stable internet connection. By combining online identity checks, advanced electronic signatures, and AI-driven fraud detection, it diminishes the need for in-person appointments and physical paperwork. This flexibility benefits both individuals finalizing personal matters—like powers of attorney or affidavits—and businesses dealing with high volumes of contracts that need swift turnaround. And thanks to meticulous encryption and data safeguards, Legitify meets stringent regulatory standards while preserving user confidence. Its success story showcases how reimagining traditional procedures can foster greater efficiency, accessibility, and overall trust in the legal domain.</p>",
                                                LocalDate.now().minusDays(23)),
                                new Post(
                                                whistleSystem,
                                                "Compliance Solutions",
                                                "Tools for whistleblower compliance.",
                                                "<h2>WhistleSystem</h2><p><strong>Compliance Solutions</strong></p><p>WhistleSystem stands at the intersection of corporate ethics and legal compliance by offering a secure channel for employees, vendors, or clients to report suspicious or unlawful activities. Designed with anonymity in mind, it encourages individuals to speak up without fearing retaliation or negative repercussions. As reports flow in, the system categorizes and escalates them based on urgency and severity, ensuring swift attention to critical issues. For leadership teams, WhistleSystem acts as an early detection mechanism, stopping small fires from turning into uncontrollable blazes of public scandal. By embedding transparency into everyday operations, it fosters a culture of integrity, proving that accountability and diligence go hand in hand with business success.</p>",
                                                LocalDate.now().minusDays(12)),
                                new Post(
                                                beCause,
                                                "Sustainability Management",
                                                "AI-powered platform for businesses.",
                                                "<h2>BeCause</h2><p><strong>Sustainability Management</strong></p><p>BeCause aligns profit and planet by offering an all-in-one platform that translates corporate sustainability goals into actionable, data-driven steps. Through rigorous analytics, it pinpoints where companies can reduce resource consumption, track carbon footprints, and refine supply chains for greater efficiency. This removes the guesswork from ‘going green,’ allowing leaders to showcase concrete results to stakeholders such as customers, partners, and shareholders. The platform’s AI-backed tools regularly update benchmarks as a business evolves, so goals remain both ambitious and realistic. By illuminating the path to meaningful change, BeCause helps businesses pivot toward a model where environmental responsibility and financial health reinforce each other.</p>",
                                                LocalDate.now().minusDays(18)),
                                new Post(
                                                chronosHub,
                                                "Simplifying Academic Publishing",
                                                "Tools for compliance and reporting.",
                                                "<h2>ChronosHub</h2><p><strong>Simplifying Academic Publishing</strong></p><p>ChronosHub tackles the labyrinth of academic publishing by bringing grant guidelines, open-access policies, and submission portals under one unifying framework. Researchers can browse suitable journals, compare publication fees, and even check compliance with multiple funding bodies, all from a single interface. By automating administrative tasks, ChronosHub liberates scholars to invest more time in actual research—formulating hypotheses, designing experiments, and analyzing results. Furthermore, the platform fosters synergy between academia and industry, bridging the gap for collaborative projects that transcend traditional siloed domains. In short, ChronosHub transforms the publishing journey from an overwhelming chore into an efficient gateway that promotes knowledge exchange on a global scale.</p>",
                                                LocalDate.now().minusDays(15)));

                posts.forEach(post -> {
                        switch (post.getStartup().getName()) {
                                case "FlavorScale":
                                        post.addOds(zeroHunger);
                                        post.addOds(industryInnovation);
                                        break;
                                case "Climaider":
                                        post.addOds(climateAction);
                                        post.addOds(responsibleConsumption);
                                        break;
                                case "Seasony":
                                        post.addOds(zeroHunger);
                                        post.addOds(sustainableCities);
                                        break;
                                case "Tribe":
                                        post.addOds(reducedInequalities);
                                        break;
                                case "Peltarion":
                                        post.addOds(goodHealthWellBeing);
                                        post.addOds(affordableCleanEnergy);
                                        break;
                                case "Donkey Republic":
                                        post.addOds(sustainableCities);
                                        post.addOds(partnershipsForTheGoals);
                                        break;
                                case "Pick Your Pour AS":
                                        post.addOds(qualityEducation);
                                        post.addOds(genderEquality);
                                        break;
                                case "Alice AI":
                                        post.addOds(qualityEducation);
                                        post.addOds(decentWorkAndEconomicGrowth);
                                        break;
                                case "LEIA Health":
                                        post.addOds(genderEquality);
                                        post.addOds(goodHealthWellBeing);
                                        break;
                                case "RightHub":
                                        post.addOds(genderEquality);
                                        post.addOds(decentWorkAndEconomicGrowth);
                                        break;
                                case "Memmora":
                                        post.addOds(cleanWaterSanitation);
                                        post.addOds(sustainableCities);
                                        break;
                                case "SoilSense":
                                        post.addOds(cleanWaterSanitation);
                                        post.addOds(responsibleConsumption);
                                        break;
                                case "Monta":
                                        post.addOds(affordableCleanEnergy);
                                        post.addOds(sustainableCities);
                                        break;
                                case "Doublepoint":
                                        post.addOds(decentWorkAndEconomicGrowth);
                                        post.addOds(industryInnovation);
                                        break;
                                case "Strise":
                                        post.addOds(decentWorkAndEconomicGrowth);
                                        post.addOds(peaceJusticeAndStrongInstitutions);
                                        break;
                                case "Station":
                                        post.addOds(qualityEducation);
                                        post.addOds(industryInnovation);
                                        break;
                                case "Silvi":
                                        post.addOds(goodHealthWellBeing);
                                        post.addOds(qualityEducation);
                                        break;
                                case "Nutrish.ai":
                                        post.addOds(goodHealthWellBeing);
                                        post.addOds(responsibleConsumption);
                                        break;
                                case "KodiakHub":
                                        post.addOds(goodHealthWellBeing);
                                        post.addOds(genderEquality);
                                        break;
                                case "EcoTree":
                                        post.addOds(responsibleConsumption);
                                        post.addOds(climateAction);
                                        break;
                                case "GoMore":
                                        post.addOds(sustainableCities);
                                        post.addOds(responsibleConsumption);
                                        break;
                                case "GoodWings":
                                        post.addOds(climateAction);
                                        post.addOds(partnershipsForTheGoals);
                                        break;
                                case "GlintSolar":
                                        post.addOds(affordableCleanEnergy);
                                        post.addOds(climateAction);
                                        break;
                                case "Spritju":
                                        post.addOds(affordableCleanEnergy);
                                        post.addOds(sustainableCities);
                                        break;
                                case "DeepBlu":
                                        post.addOds(goodHealthWellBeing);
                                        post.addOds(lifeBelowWater);
                                        break;
                                case "Vove":
                                        post.addOds(responsibleConsumption);
                                        post.addOds(climateAction);
                                        break;
                                case "PerPlant":
                                        post.addOds(responsibleConsumption);
                                        post.addOds(climateAction);
                                        break;
                                case "Dripdrop":
                                        post.addOds(responsibleConsumption);
                                        post.addOds(lifeBelowWater);
                                        break;
                                case "Legitify":
                                        post.addOds(peaceJusticeAndStrongInstitutions);
                                        break;
                                case "WhistleSystem":
                                        post.addOds(decentWorkAndEconomicGrowth);
                                        post.addOds(reducedInequalities);
                                        break;
                                case "BeCause":
                                        post.addOds(responsibleConsumption);
                                        post.addOds(partnershipsForTheGoals);
                                        break;
                                case "ChronosHub":
                                        post.addOds(industryInnovation);
                                        post.addOds(peaceJusticeAndStrongInstitutions);
                                        break;
                                default:
                                        System.out.println("No se encontró el post de la startup: "
                                                        + post.getStartup().getName());
                                        break;
                        }

                        if (service.findByTitle(post.getTitle()) == null) {
                                service.save(post);

                                Comment newComment = new Comment();
                                newComment.setComment("I love " + post.getTitle());
                                newComment.setPost(post);
                                newComment.setUser(flavorScale); // Example user

                                flavorScale.addComment(newComment);

                                commentService.save(newComment);
                                startupService.save(flavorScale);
                                service.save(post);
                        }
                });
        }
}